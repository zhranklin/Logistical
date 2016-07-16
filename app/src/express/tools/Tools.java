package express.tools;

import com.sun.istack.internal.NotNull;
import express.model.Order;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Zhranklin on 16/7/2.
 */
public class Tools {
  public static interface ErrorHandler {
    public void onError(String msg);
  }
  public static final int  RECORD_LENGTH = 10;
  public static String orderToCsv(@NotNull Order order) {
    StringBuilder sb = new StringBuilder();
    for (String name : Order.ATTRIBUTE_NAMES) {
      sb.append(order.getAttribute(name))
        .append(", ");
    }
    for (String name : Order.FEE_NAMES) {
      sb.append(order.getFee(name))
        .append(", ");
    }
    return sb
      .append(order.getTotalNumber())
      .append(", ")
      .append(order.getTotalFee())
      .append(", ").toString();
  }

  public static Order csvToOrder(@NotNull String line) throws IllegalArgumentException {
    HashMap<String, String> attributes = new HashMap<>();
    HashMap<String, Integer> fees = new HashMap<>();
    String[] entries = line.split("\\s*,\\s*");
    if (entries.length <= RECORD_LENGTH)
      throw new IllegalArgumentException("条目数目太少");
    int i = 0;
    for (String name : Order.ATTRIBUTE_NAMES)
      attributes.put(name, entries[i++]);
    for (String name : Order.FEE_NAMES) {
      fees.put(name, Integer.valueOf(entries[i++]));
    }
    return new Order(attributes, fees, Collections.EMPTY_LIST);
  }

  public static List<Order> fileToOrder(@NotNull File file, ErrorHandler eh) {
    LinkedList<Order> orders = new LinkedList<>();
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(file));
      String line;
      while ((line = br.readLine()) != null) {
        try {
          orders.add(csvToOrder(line));
        } catch (IllegalArgumentException e) {
          // TODO: 添加错误信息
        }
      }
    } catch (IOException e) {
      eh.onError("文件读取错误");
    } finally {
      try {
        if (br != null)
          br.close();
      } catch (IOException e) {}
    }
    return orders;
  }
}
