import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class FileUploadService {
  private baseUrl = 'https://web-only-chat.herokuapp.com/api/user';

  constructor(private http: HttpClient) { }

  upload(email: string, file: File): Observable<HttpEvent<any>> {
    
    const formData: FormData = new FormData();
    formData.append('file', file);
    
    const req = new HttpRequest('POST', `${this.baseUrl}/upload/${email}`, formData, {
      reportProgress: true,
      responseType: 'json'
    });
    return this.http.request(req);
  }
}