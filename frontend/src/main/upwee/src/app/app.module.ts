import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MdListModule, MdDialogModule, MdInputModule, MdIconModule, MdToolbarModule, MdButtonModule, MdCheckboxModule, MdCardModule, MdProgressBarModule} from '@angular/material';
import { AppComponent } from './app.component';
import { LoginCardComponent } from './login-card/login-card.component';
import { AuthService } from './services/auth.service';
import {ApiUtils} from './services/api-utils';
import { FileService } from './services/file.service';
import {MdSnackBarModule} from '@angular/material';
import { RouterModule, Routes } from '@angular/router';
import { FileManagmentComponent } from './file-managment/file-managment.component';
import { DeletionDialog, CreationDialog, UploadDialog } from './file-managment/file-managment.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { RegisterComponent } from './register/register.component';
import { DragulaModule } from 'ng2-dragula/ng2-dragula';
import { DraggableDirective } from './drag/draggable.directive';
import { DroptargetDirective } from './drag/droptarget.directive';
import { DragService } from './drag/drag.service';

const appRoutes: Routes = [
  { path:'login', 
    component:LoginCardComponent
  },
  { path:'register', 
    component:RegisterComponent
  },
  { path:'files/:path', 
    component:FileManagmentComponent
  },
  { path: '',
      redirectTo: '/login',
      pathMatch: 'full'
  },
  { path: '**', 
      component: PageNotFoundComponent 
  }
]

@NgModule({
  declarations: [
    AppComponent,
    LoginCardComponent,
    FileManagmentComponent,
    DeletionDialog,
    UploadDialog,
    CreationDialog,
    PageNotFoundComponent,
    RegisterComponent,
    DraggableDirective,
    DroptargetDirective
  ],
  imports: [
    BrowserAnimationsModule,
    BrowserModule,
    FormsModule,
    RouterModule.forRoot(appRoutes),
    MdButtonModule,
    MdCheckboxModule,
    MdProgressBarModule,
    MdCardModule,
    MdListModule,
    MdToolbarModule,
    MdInputModule,
    MdDialogModule,
    DragulaModule,
    MdSnackBarModule,
    MdIconModule,
    HttpModule
  ],
  providers: [AuthService, ApiUtils, FileService, DragService],
  bootstrap: [AppComponent],
  entryComponents: [ DeletionDialog, UploadDialog, CreationDialog ]
})
export class AppModule { }
