import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Friend } from './Friend';

@Injectable({
  providedIn: 'root'
})
export class AddfriendService {

  constructor(private http: HttpClient) { }

  private API_USER_ME: string = `http://localhost:8082/api/user/1/adicionar`;
  private API_ADD_USER: string = `http://localhost:8082/api/user/${localStorage.getItem("userId")}/contact`;

  findNewsFriend(findString: any): Observable<Friend[]> {
    {
      const httpOptions = {
        headers: { authorization: 'Basic ' + localStorage.getItem("authorization") },
        params: { 'adicionar': findString }
      };

      return this.http.get<Friend[]>(this.API_USER_ME, httpOptions)
    }
  }

  addfriend(id: any): Observable<any> {
    {
      console.log(id)
      const httpOptions = {
        headers: { authorization: 'Basic ' + localStorage.getItem("authorization") },
        body: { 'id': id }
      };

      return this.http.post<any>(this.API_ADD_USER, { 'id': id })
    }
  }
}
