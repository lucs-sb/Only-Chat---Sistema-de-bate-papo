import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { StorageService } from './storage.service';
import { MessagePage } from './messagePage';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  private API_USER_LOGIN: string = 'https://web-only-chat.herokuapp.com/api/login/';
  private API_MESSAGE: string = 'https://web-only-chat.herokuapp.com/api/';


  constructor(private http: HttpClient, private localStorage: StorageService) {
    this.getFriendForCard()
  }

  getFriendForCard() {
    {
      const httpOptions = {
        headers: { authorization: 'Basic ' + localStorage.getItem("authorization") }
      };

      return this.http.get<any>(this.API_USER_LOGIN+`${localStorage.getItem("EmailToChat")!}`, httpOptions).pipe(
        tap((response: User) => {
          this.localStorage.set('ChatUserId', response.id.toString());
          this.localStorage.set('ChatUserEmail', response.email);
          this.localStorage.set('ChatUserName', response.name);
          this.localStorage.set('ChatUserGender', response.gender);
          this.localStorage.set('ChatUserPhoto', response.url_photo);
          return response
        })
      )
    }
  }

  sendMessage(content: any) {
    {
      var body = {
        'message': content
      }

      return this.http.post<any>(this.API_MESSAGE+`${localStorage.getItem("userId")}/message/${localStorage.getItem("ChatUserId")}`, body).pipe(
        tap(response => {
          return response
        })
      )
    }
  }

  getMessages(pageNumber: number): Observable<MessagePage> {
    {
      return this.http.get<MessagePage>(this.API_MESSAGE+`${localStorage.getItem("userId")}/message/${localStorage.getItem("ChatUserId")}?size=10&page=${pageNumber}`)
    }
  }
}
