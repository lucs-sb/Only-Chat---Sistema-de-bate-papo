import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ChatService } from '../chat.service';
import { Friend } from '../Friend';
import { Message } from '../Message';
import { MessagePage } from '../messagePage';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {
  userId: Number = +localStorage.getItem("userId")!
  userChatName: string = localStorage.getItem("ChatUserName")!
  userChatEmail: string = localStorage.getItem("ChatUserEmail")!
  userChatGender: string = localStorage.getItem("ChatUserGender")!
  photo: string = localStorage.getItem("ChatUserPhoto")!
  messageContent: string = ''
  messages: Message[] = [];
  messagePage: MessagePage | undefined;
  pageNumber: number = 0;
  size: number = 12;
  @ViewChild('scrollMe')
  
  private myScrollContainer!: ElementRef;
  disableScrollDown = false

  constructor(private chatService: ChatService, private route: ActivatedRoute) {

  }

  ngOnInit(): void {
    this.getFriendForCard()
    this.getMessages()
    setInterval(() => {
     this.getMessages()
    }, 2000);
  }
  ngAfterViewChecked() {
    this.scrollToBottom();
}

 onScroll() {
    let element = this.myScrollContainer.nativeElement
    let atBottom = element.scrollHeight - element.scrollTop === element.clientHeight
    this.pageNumber++;
    if (this.disableScrollDown && atBottom) {
        this.disableScrollDown = false
    } else {
        this.disableScrollDown = true
    }
}


scrollToBottom(): void {
    if (this.disableScrollDown) {
        return
    }
    try {
        this.myScrollContainer.nativeElement.scrollTop = this.myScrollContainer.nativeElement.scrollHeight;
    } catch(err) { }
}
  getFriendForCard(): void {
    this.chatService.getFriendForCard().subscribe();
  }

  sendMessage(): void {
    this.chatService.sendMessage(this.messageContent).subscribe();
    this.messageContent = ''
    this.getMessages()
  }

  getMessages(): void {
    this.chatService.getMessages(this.pageNumber).subscribe((messages) => (this.messages = messages.content));
  }
}
