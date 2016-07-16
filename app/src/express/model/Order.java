package express.model;

import com.sun.istack.internal.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Order {
  //TODO 变成guava中的不可变容器
  public static final List<String> ATTRIBUTE_NAMES =
    Arrays.asList("发站", "到站",
      "发货人", "发货人电话", "收货人", "收货人电话",
      "客户单号", "付款方式", "返款方", "返款方式1", "返款方式2");

  public static final List<String> FEE_NAMES =
    Arrays.asList("运费", "代收款", "返款费", "保价费", "接货费", "送货费");

  private Map<String, String> attributes;
  private Map<String, Integer> fees;
  private List<Staff> staff;

  public Order(@NotNull Map<String, String> attributes, @NotNull Map<String, Integer> fees, @NotNull List<Staff> staff) {
    this.attributes = attributes;
    this.fees = fees;
    this.staff = staff;
  }

  public List<Staff> getStaff() {
    return staff;
  }

  public String getAttribute(@NotNull String name) throws IllegalArgumentException {
    if (!ATTRIBUTE_NAMES.contains(name))
      throw new IllegalArgumentException("该属性名称不存在");
    String attr = attributes.get(name);
    if (attr == null)
      attr = "";
    return attr;
  }

  public int getFee(@NotNull String name) throws IllegalArgumentException {
    if (!FEE_NAMES.contains(name))
      throw new IllegalArgumentException("该费用名称不存在");
    if (name.equals("运费")) {
      int tot = 0;
      for (Staff s: staff) {
        tot += s.getNumber() * s.getPrice();
      }
      return tot;
    }
    Integer fee = fees.get(name);
    if (fee == null)
      fee = 0;
    return fee;
  }

  public int getTotalFee(){
    int tot = 0;
    for (String n: ATTRIBUTE_NAMES) {
      tot += getFee(n);
    }
    return tot;
  }

  public int getTotalNumber() {
    int tot = 0;
    for (Staff s : staff) {
      tot += s.getNumber();
    }
    return tot;
  }
}

