# jgatttool - Java library for Bluetooth LE GATT 

## Overview

This library provides Java API for reading and writing values exposed by a Bluetooth Low Energy device such as sensors, wearable, etc. BLE bases his data model on Generic  Attribute Profile (GATT).

jgatttool is a wrapper of 'gatttool' command-line utility and it uses BlueZ (requires Linux).

## Motivation

It is difficult to find a cross-platform BLE library for any language except for Smartphone's most used programming languages. 'gatttool' is a great command-line tool that works great on Linux but it's written in C and there aren't similiar well documented libraries or tools. So I wrote myself this jgatttool wrapper to easily write and read BLE devices' values in Java.

## Next improvements

* Parsing of all data types
* Notifications management
* Request timeout management
