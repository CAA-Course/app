import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Order, OrderResult } from '../models/order.model';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  private orderURL = '/api/orders';

  constructor(private httpClient: HttpClient) {}

  createOrder(order: Order): Observable<OrderResult[]> {
    return this.httpClient.post<OrderResult[]>(this.orderURL, order);
  }
}
