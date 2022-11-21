# TODO

## Hardware
* Disable all digital pins until WiFi connection complete
  * If the digital pins are connected at boot time, the controller crashes. These should be grounded by default and only enabled after the WiFi connection is complete
* Transfer driver chips & motor plugs to bread board
* Share power source between controller & motors
* Carriage
  * Frame
  * Mounts
  * Axels
  * 5mm Gears
  * Wheels

## Software
* Websocket
  * Authentication
  * Rate Limiting
  * Revival