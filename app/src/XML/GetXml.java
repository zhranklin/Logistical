public class GetXml{
    public static final ATTRIBUTE_NAMES[][] =
    {
                new Arrays("发站", "到站"),
                new Arrays("发货人", "发货人电话"),
                new Arrays("收货人", "收货人电话"),
                new Arrays("付款方式", "品类", "小类", "件数", "单价", "已付", "提付", "月结"),
                new Arrays("代收款", "返款放", "保价费", "接货费", "送运费"),
                new Arrays("总件数","总运费")
    };

    public static final List<String> FEE_NAMES =
            Arrays.asList("运费", "代收款", "返款费", "保价费", "接货费", "送货费");
    public static final List<String> INFO=
            Arrays.asList("基本信息","发货人信息","收货人信息","货物信息","价格信息","合计信息")
    public static void main(String args[]) {

    }
}