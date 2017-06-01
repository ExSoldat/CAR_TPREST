import { Component, OnInit } from '@angular/core';
import { MdListModule, MdDialogModule, MdDialogRef, MdDialog} from '@angular/material';
import { FileService } from '../services/file.service';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { MdSnackBar } from '@angular/material';
import { DraggableDirective } from '../drag/draggable.directive';

@Component({
  selector: 'app-file-managment',
  templateUrl: './file-managment.component.html',
  styleUrls: ['./file-managment.component.css']
})
export class FileManagmentComponent implements OnInit {
  selectedOption: string;
  showGoBackArrow : boolean = false;
  navigationHistory : any[] = [];
  path = "/";
  data : any;
  loading = false;

  constructor(private auth : AuthService, 
    public dialog: MdDialog, 
    private fileservice : FileService, 
    private router:Router, 
    private snackbar: MdSnackBar) {
    if(!auth.isAuthenticated()) {
      this.router.navigate(['/login']);
    }
  }

  ngOnInit() {
    this.listFiles(this.path);
  }

  openDeletionDialog(path:string, filename:string, event) {
    event.stopPropagation();
    let dialogRef = this.dialog.open(DeletionDialog);
    dialogRef.componentInstance.filename = filename;
    dialogRef.afterClosed().subscribe(result => {
      if(result === true)
        this.deleteFile(path, filename);
    });
  }

  openUploadDialog(path:string) {
    let dialogRef = this.dialog.open(UploadDialog);
    dialogRef.componentInstance.path = path;
    dialogRef.afterClosed().subscribe(result => {
      if(result)
        this.uploadFile(result);
    },
      error => {
        this.loading = false;
        this.showError("We were unable to reach the server. Please try again");
      });
  }

  openCreationDialog() {
    let dialogRef = this.dialog.open(CreationDialog);
    dialogRef.afterClosed().subscribe(result => {
      if(result !== false)
        this.createFolder(result);
    });
  }

  deleteFile(path : string, filename : string) {
    this.loading = true;
    console.log("deleting " + path + filename);
    this.fileservice.apiDeleteFile(path, filename).subscribe(result => {
      this.loading = false;
      if (result === true) {
        this.listFiles(this.path);
      } else {
        this.showError(result);
      }
    });
  }

  createFolder(foldername : string) {
    this.loading = true;
    this.fileservice.apiCreateFolder(this.path, foldername).subscribe(result => {
      this.loading = false;
      if(result === true) {
        this.listFiles(this.path);
      } else {
        this.showError(result);
      }
    });
  }

  downloadFile(path : string, filename : string) {
    console.log("downloading " + filename);
    window.open(this.fileservice.apiDownloadFile(path, filename));
  }

  moveInto(f : any) {
    if(f.type == 'folder') {
      this.navigationHistory.push(f);
      this.path = this.path + f.file_name + "/";
      this.showGoBackArrow = this.path !="/";
      this.listFiles(this.path);
    }
  }

  goback() {
    this.moveTo(this.navigationHistory.length-1);
  }

  moveTo(index : number) {
    if(index <= this.navigationHistory.length) {
      this.navigationHistory.length = index;
      this.path = this.getPathFromNavigationHistory();
      this.listFiles(this.path);
      this.showGoBackArrow = this.path !="/";
    }
  }

  getPathFromNavigationHistory() : string {
    var r : string = "/";
    for(var nf in this.navigationHistory) {
      r += this.navigationHistory[nf].file_name + "/"
    }
    return r;
  }

  listFiles(fullpath : string):any {
    this.loading = true;
    this.fileservice.apiListFiles(this.path).subscribe(result => {
      if(result === false) {
        this.showError('The file does not exist anymore :(');
        this.goback();
      } else {
        this.data = result;
        this.loading = false;
      }
    });
  }

  moveFile(file, target : string) {
    console.log('moving ' + file + ' into ' + target);
    this.loading = true;
    this.fileservice.apiMoveFile(file, target, this.path).subscribe(result => {
      this.loading = false;
      if(result === true) {
        this.listFiles(this.path);
      } else {
        this.showError(result);
      }
    });
  }

  moveFileUpwards(file, targetIndex : number) {
    console.log('moving ' + file + ' into ' + targetIndex);
    var remainingPath : string = "/";
    for(var i = targetIndex; i<this.navigationHistory.length; i++) {
      remainingPath += this.navigationHistory[i].file_name + "/"
    }
    this.moveTo(targetIndex);
    this.moveFile(remainingPath+file, '');
  }

  uploadFile(f : File) {
    this.loading = true;
    this.fileservice.apiUploadFile(this.path, f).subscribe(result => {
      this.loading = false;
      if (result === true) {
        this.listFiles(this.path);
      } else {
        this.showError(result);
      }
    });
  }

  showError(message : string) {
    this.snackbar.open(message, '', {duration : 5000});
  };
}

@Component({
  selector: 'delete-file-dialog',
  templateUrl: './delete-file-dialog.html',
})
export class DeletionDialog {
  filename:string;
  constructor(public dialogRef: MdDialogRef<DeletionDialog>) {}
}

@Component({
  selector: 'upload-file-dialog',
  templateUrl: './upload-file-dialog.html',
  styleUrls: ['./upload-file-dialog.css']
})
export class UploadDialog {
  path:string;
  file: File;
  filename : string = '';
  filesizeok :boolean = true;
  errormessagecolor = '#999';
  constructor(public dialogRef: MdDialogRef<UploadDialog>) {}

  openFileExplorer() {
    document.getElementById('fileinput').click();
  }

  onChange(event: EventTarget) {
      let eventObj: MSInputMethodContext = <MSInputMethodContext> event;
      let target: HTMLInputElement = <HTMLInputElement> eventObj.target;
      let files = target.files;
      this.file = files[0];
      if(this.file.size >= 128000) {
        this.filesizeok = false; 
        this.errormessagecolor='#D50000';
      } else {
        this.filesizeok = true;
        this.errormessagecolor='#999';
      }
      console.log(this.filesizeok);
      this.filename = this.file.name;
  }
}

@Component({
  selector: 'create-folder-dialog',
  templateUrl: './create-folder-dialog.html',
})
export class CreationDialog {
  foldername:string = '';
  constructor(public dialogRef: MdDialogRef<CreationDialog>) {}

  submit(event) {
    if(event.keyCode == 13) 
      document.getElementById('confirm-button').click();
  }
}