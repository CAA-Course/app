import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { Product } from '../core/models/product.model';

@Component({
  selector: 'app-product-fields',
  templateUrl: './product-fields.component.html',
  styleUrls: ['./product-fields.component.css']
})
export class ProductFieldsComponent {
  @Input() product: Product;
  @Output() saveProduct = new EventEmitter();

  constructor() { }

  doSave() {
    this.saveProduct.emit(this.product);
  }

}
