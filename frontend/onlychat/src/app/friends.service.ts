import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { Friend } from './Friend';


@Injectable({
  providedIn: 'root'
})
export class FriendsService {

  constructor(private http: HttpClient) { }
  private API_USER_ME: string = `https://web-only-chat.herokuapp.com/api/user/${localStorage.getItem("userId")}/contact`;
  private API_FIND_BY_NAME: string = `https://web-only-chat.herokuapp.com/api/user/${localStorage.getItem("userId")}/busca`;

  getFriends(): Observable<Friend[]> {
    {
      const httpOptions = {
        headers: { authorization: 'Basic ' + localStorage.getItem("authorization") },
      };

      return this.http.get<Friend[]>(this.API_USER_ME, httpOptions)
    }
  }

  sendRemoveFriend(id: any) {
    const httpOptions = {
      headers: { authorization: 'Basic ' + localStorage.getItem("authorization") },
    };

    return this.http.delete<Object>(this.API_USER_ME + '/' + id, httpOptions)

  }

  findFriends(findString: any): Observable<Friend[]> {
    {
      const httpOptions = {
        headers: { authorization: 'Basic ' + localStorage.getItem("authorization") },
        params: { 'busca': findString }
      };

      return this.http.get<Friend[]>(this.API_FIND_BY_NAME, httpOptions)
    }
  }
}
