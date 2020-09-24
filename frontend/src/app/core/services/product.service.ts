import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductHeader } from '../models/product-header.model';
import { Product } from '../models/product.model';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private productURL = '/api/products';

  constructor(private httpClient: HttpClient) {}

  getProducts(): Observable<ProductHeader[]> {
    return this.httpClient.get<Array<ProductHeader>>(this.productURL);
  }

  getProduct(id: number): Observable<Product> {
    return this.httpClient.get<Product>(this.productURL + '/' + id);
  }

  addProduct(product: Product): Observable<Product> {
    return this.httpClient.post<Product>(this.productURL, product);
  }

  updateProduct(product: Product): Observable<Product> {
    return this.httpClient.put<Product>(
      this.productURL + '/' + product.id,
      product
    );
  }

  deleteProduct(id: number): Observable<{}> {
    return this.httpClient.delete(this.productURL + '/' + id);
  }
}
