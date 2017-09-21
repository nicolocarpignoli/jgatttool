# jgatttool - Java library for Bluetooth LE GATT 

## Overview

This library provides Java APIs for reading and writing values exposed by a Bluetooth Low Energy device such as sensors, wearable, etc. BLE bases his data model on Generic  Attribute Profile (GATT). With jgatttool you can develop your own BLE central device in Java.

jgatttool is a wrapper of `gatttool`, a command-line utility that uses BlueZ stack (requires Linux).

## Motivation

It is difficult to find a cross-platform BLE library for any language except for smartphone platforms' most used programming languages. `gatttool` is a great command-line tool that works great on Linux but it's written in C and it's not documented, and there aren't similar documented libraries or tools. So I wrote myself this gatttool wrapper to easily write and read GATT properties in Java.
You can use this library in any Linux system that includes `gatttool` (Linux Desktop computers, WindRiver Linux gateways, RaspberryPis, etc.).

## Examples

On the Example directory you can find a use case example for jgattool library. The system represent an IoT architecture with focus on the communication between a Gateway device and a Genuino101 device. To run the example you have to configure the connection strings and addressed of devices and mock components that are not uploaded - because they are private.
The only aim of this example is to show a real use case of the library.

## Documentation

The code is pretty much self-explained and it's basically a set of APIs for convert and parse data.

## Contributing

Pull-requests and Issues are welcome.

## Next improvements

* Parsing of all data types
* Notifications management


## License

This project is released under MIT license as you can check in LICENSE.md file.
