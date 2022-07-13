import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ChatService } from '../chat.service';
import { Friend } from '../Friend';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  constructor(private chatService: ChatService, private route: ActivatedRoute) {
    this.getFriendForCard()
  }

  ngOnInit(): void {
  }

  getFriendForCard(): void {
    var userCardId = this.route.snapshot.paramMap.get('id');

    this.chatService.getFriendForCard(userCardId).subscribe();
  }

}
