# SFO Module

# this module contains all rests that needed for sfo project

## reverse geocode
* URL: http://localhost:8989/reverse-geocode
* Content-Type: application/json

* Request Body Example:
```json
{
  "lat": 35.70069889205458,
  "lon": 51.336722455526775
}
```

* Response Body Example:
```json
{
    "car_subnetwork": "false",
    "road_class_link": "false",
    "road_access": "yes",
    "road_environment": "road",
    "road_class": "trunk",
    "max_speed": "30.0 | 30.0",
    "car_average_speed": "28.0 | 28.0",
    "car_access": "true | false",
    "ferry_speed": "0.0",
    "street_name": "میدان آزادی",
    "roundabout": "true"
}
```



