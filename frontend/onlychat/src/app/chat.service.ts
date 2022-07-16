import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Friend } from './Friend';
import { tap } from 'rxjs/operators';
import { StorageService } from './storage.service';
import { Message } from './Message';
import { MessagePage } from './messagePage';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  private API_USER_LOGIN: string = 'http://localhost:8080/api/user/login';
  private API_SEND_MESSAGE: string = 'http://localhost:8080/api/user/message';

  constructor(private http: HttpClient, private localStorage: StorageService) {
    this.getFriendForCard()
  }

  getFriendForCard() {
    {
      const httpOptions = {
        headers: { authorization: 'Basic ' + localStorage.getItem("authorization") }
      };

      return this.http.get<any>(this.API_USER_LOGIN+`${localStorage.getItem("EmailToChat")!}`, httpOptions).pipe(
        tap(response => {
          this.localStorage.set('ChatUserId', response.id);
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
        'sender': localStorage.getItem("userId"),
        'receiver': localStorage.getItem("ChatUserId"),
        'message': content
      }

      return this.http.post<any>(this.API_SEND_MESSAGE, body).pipe(
        tap(response => {
          return response
        })
      )
    }
  }

  getMessages(pageNumber: number): Observable<MessagePage> {
    {
      var url = `http://localhost:8080/api/user/${localStorage.getItem("userId")}/message/${localStorage.getItem("ChatUserId")}?size=10&page=${pageNumber}`
      return this.http.get<MessagePage>(url)
    }
  }
}
