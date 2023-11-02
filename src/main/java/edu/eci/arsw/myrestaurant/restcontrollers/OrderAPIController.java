/*
 * Copyright (C) 2016 Pivotal Software, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.arsw.myrestaurant.restcontrollers;

import edu.eci.arsw.myrestaurant.model.OrderResponse;
import edu.eci.arsw.myrestaurant.services.RestaurantOrderServices;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author hcadavid
 */
@RestController
@RequestMapping("/orders")
public class OrderAPIController {

    @Autowired
    private final RestaurantOrderServices orderServices;


    public OrderAPIController(RestaurantOrderServices orderServices) {
        this.orderServices = orderServices;
    }

    @GetMapping
    public ResponseEntity<?> getOrders() {
        try {
            Map<Integer, OrderResponse> orders = orderServices.getOrders();
            JSONObject jsonObject = new JSONObject();
            JSONArray ordersArray = new JSONArray();

            for (Map.Entry<Integer, OrderResponse> entry : orders.entrySet()) {
                JSONObject orderJson = new JSONObject();
                orderJson.put("tableNumber", entry.getValue().getOrder().getTableNumber());
                orderJson.put("total", entry.getValue().getTotal());

                JSONArray dishesArray = new JSONArray();
                Map<String, Integer> dishMap = entry.getValue().getOrder().getOrderAmountsMap();
                for (Map.Entry<String, Integer> dishEntry : dishMap.entrySet()) {
                    JSONObject dishJson = new JSONObject();
                    dishJson.put("name", dishEntry.getKey());
                    dishJson.put("quantity", dishEntry.getValue());
                    dishesArray.put(dishJson);
                }
                orderJson.put("dishes", dishesArray);

                ordersArray.put(orderJson);
            }

            jsonObject.put("orders", ordersArray);

            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>("No funcionó", HttpStatus.BAD_REQUEST);
        }
    }



}





