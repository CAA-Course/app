export class Order {
  customerId: number;
  products: OrderProduct[];
}

export interface OrderProduct {
  id: number;
  quantity: number;
}
