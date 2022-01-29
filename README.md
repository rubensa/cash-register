# Cash Register

This repo is an exercise to manage a store cash register.

## Exercise

The store will sell the following 3 products.

| CODE    | NAME           | PRICE  |
| ------- | -------------- | ------ |
| VOUCHER | Gift Card      | 5.00€  |
| TSHIRT  | Summer T-Shirt | 20.00€ |
| PANTS   | Summer Pants   | 7.50€  |

The different departments have agreed the following discounts:
  * A 2-for-1 special on VOUCHER items.
  * If you buy 3 or more TSHIRT items, the price per unit should be 19.00€.
  * Items can be scanned in any order, and the cashier should return the total amount to be paid.

The interface for the checkout process has the following specifications:
  * The Checkout constructor receives a pricing_rules object
  * The Checkout object has a scan method that receives one item at a time

Examples:
  * Items: VOUCHER, TSHIRT, PANTS - Total: 32.50€
  * Items: VOUCHER, TSHIRT, VOUCHER - Total: 25.00€
  * Items: TSHIRT, TSHIRT, TSHIRT, VOUCHER, TSHIRT - Total: 81.00€
  * Items: VOUCHER, TSHIRT, VOUCHER, VOUCHER, PANTS, TSHIRT, TSHIRT - Total: 74.50€

## Development

This repo is aimed to be opened in **[VSCode](https://code.visualstudio.com/)** with the **[Remote Development](https://code.visualstudio.com/docs/remote/remote-overview)** extension pack installed.

The development is done inside a **[Docker container](https://docker.com/)** that installs and configures the required tools on first run.

Please, make sure you have **VSCode** with the **Remote Development extension pack** and **Docker** installed and working on your system before cloning the respository then clone de repository:

`git clone https://github.com/rubensa/cash-register.git`

For detailed instructions see: **[Developing inside a Container](https://code.visualstudio.com/docs/remote/containers)**.

This project contains a [VS Code multi-root workspace](https://code.visualstudio.com/docs/editor/multi-root-workspaces).

### Project Organization

    ├── .devcontainer                 <- VSCode Remote Development configuration
    │
    ├── .vscode                       <- VSCode configuration
    │
    ├── cash-register.code-workspace  <- VSCode workspace configuration file
    ├── LICENSE                       <- The License file
    └── README.md                     <- The README for developers using this project
