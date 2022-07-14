import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { NotifierModule, NotifierOptions } from 'angular-notifier';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpBasicAuthInterceptor } from './http-basic-auth.interceptor';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { FriendsComponent } from './friends/friends.component';
import { HeaderComponent } from './header/header.component';
import { AddfriendComponent } from './addfriend/addfriend.component';
import { ChatComponent } from './chat/chat.component';

const customNotifierOptions: NotifierOptions = {
  position: {
    horizontal: {
      position: 'middle',
      distance: 12
    },
    vertical: {
      position: 'top',
      distance: 12,
      gap: 10
    }
  },
  theme: 'material',
  behaviour: {
    autoHide: 1500,
    onClick: 'hide',
    onMouseover: 'pauseAutoHide',
    showDismissButton: true,
    stacking: 4
  },
  animations: {
    enabled: true,
    show: {
      preset: 'slide',
      speed: 300,
      easing: 'ease'
    },
    hide: {
      preset: 'fade',
      speed: 300,
      easing: 'ease',
      offset: 50
    },
    shift: {
      speed: 300,
      easing: 'ease'
    },
    overlap: 150
  }
};

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    FriendsComponent,
    HeaderComponent,
    AddfriendComponent,
    ChatComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    NotifierModule.withConfig(customNotifierOptions),
    InfiniteScrollModule
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: HttpBasicAuthInterceptor, multi: true }],
  bootstrap: [AppComponent],
})
export class AppModule { }
