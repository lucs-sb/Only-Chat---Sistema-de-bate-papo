import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Friend } from './Friend';
import { tap } from 'rxjs/operators';
import { StorageService } from './storage.service';
import { Message } from './Message';

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private API_USER_LOGIN: string = `http://localhost:8082/api/user/login`;
  private API_SEND_MESSAGE: string = `http://localhost:8082/api/user/message`;

  constructor(private http: HttpClient, private localStorage: StorageService) {
    this.getFriendForCard()
  }

  getFriendForCard() {
    {
      const httpOptions = {
        headers: { authorization: 'Basic ' + localStorage.getItem("authorization") },
        params: { 'email': localStorage.getItem("EmailToChat")! }
      };

      return this.http.get<any>(this.API_USER_LOGIN, httpOptions).pipe(
        tap(response => {
          this.localStorage.set('ChatUserId', response.id);
          this.localStorage.set('ChatUserEmail', response.email);
          this.localStorage.set('ChatUserName', response.name);
          this.localStorage.set('ChatUserGender', response.gender);

          return response
        })
      )
    }
  }

  sendMessage(content: any) {
    {
      var body = {
        'sender': localStorage.getItem("userId"),
        "receiver": localStorage.getItem("ChatUserId"),
        "message": content
      }

      return this.http.post<any>(this.API_SEND_MESSAGE, body).pipe(
        tap(response => {
          return response
        })
      )
    }
  }

  getMessages(): Observable<Message[]> {
    {
      var url = `http://localhost:8082/api/user/${localStorage.getItem("userId")}/message/${localStorage.getItem("ChatUserId")}`
      return this.http.get<Message[]>(url)
    }
  }
}
