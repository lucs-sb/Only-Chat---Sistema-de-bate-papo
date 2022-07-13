import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ChatService } from '../chat.service';
import { Friend } from '../Friend';
import { Message } from '../Message';

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
  messageContent: string = ''
  messages: Message[] = [];




  constructor(private chatService: ChatService, private route: ActivatedRoute) {

  }

  ngOnInit(): void {
    this.getFriendForCard()
    this.getMessages()

    setInterval(() => {
      this.getMessages()
    }, 2000);
  }

  getFriendForCard(): void {
    //this.route.snapshot.paramMap.get('id');

    this.chatService.getFriendForCard().subscribe();
  }

  sendMessage(): void {
    this.chatService.sendMessage(this.messageContent).subscribe();
    this.messageContent = ''
    this.getMessages()
  }

  getMessages(): void {
    this.chatService.getMessages().subscribe((messages) => (this.messages = messages));
  }

}
