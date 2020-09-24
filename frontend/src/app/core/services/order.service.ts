import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Order } from '../models/order.model';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  private orderURL = '/api/orders';

  constructor(private httpClient: HttpClient) {}

  createOrder(order: Order): Observable<Order> {
    return this.httpClient.post<Order>(this.orderURL, order);
  }
}
