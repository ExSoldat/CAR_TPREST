import { UpweePage } from './app.po';

describe('upwee App', () => {
  let page: UpweePage;

  beforeEach(() => {
    page = new UpweePage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
