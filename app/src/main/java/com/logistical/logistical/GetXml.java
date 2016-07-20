package com.logistical.logistical;

public class GetXml {
    public static final String ATTRIBUTE_NAMES[][] = {{"发站", "具体发站", "到站", "具体到站","客户单号"}, {"发货人", "发货人电话"},
            {"收货人", "收货人电话"}, {"当前件数","品类", "小类", "件数", "单价"}, {"付款方式", "代收款","返款费", "返款从", "到",  "保价费", "接货费", "送货费"},
            {"总件数", "总运费", "总价"}};
    public static final String ATTRIBUTE_VALUES[][] = {{"Fstation", "Fstation2", "Tstation", "Tstation2","danhao"},
            {"Fname", "Ftel"}, {"Tname", "Ttel"}, {"staffnum","category1", "category2", "number", "uniprice"},
            {"payway", "daishou","fankuan", "Ffankuan", "Tfankuan",  "baojia", "jiehuo", "songyun"},
            {"totnumber", "tottranpay", "totpay"}};
    public static final String INFO[] = {"基本信息", "发货人信息", "收货人信息", "货物信息", "价格信息", "合计信息"};
    public static final int list[][] = {{0, 2}, null, null, {0, 1,2}, {0, 3, 4}, null};

    public static void main(String args[]) {
        System.out.print(" <ScrollView\n" + "        android:layout_width=\"match_parent\"\n"
                + "        android:layout_height=\"wrap_content\">\n" + "\n" + "        <RelativeLayout\n"
                + "            android:layout_width=\"match_parent\"\n"
                + "            android:layout_height=\"wrap_content\"\n" + "            android:id=\"@+id/father\">");
        System.out.print(" <LinearLayout\n" + "                android:orientation=\"vertical\"\n"
                + "                android:layout_width=\"match_parent\"\n"
                + "                android:layout_height=\"wrap_content\"\n"
                + "                android:id=\"@+id/upper\">");
        for (int i = 0; i < INFO.length; i++) {
            if (i == 3)
                System.out.print("\n" +
                        "                <RelativeLayout\n" +
                        "                    android:layout_width=\"match_parent\"\n" +
                        "                    android:layout_height=\"match_parent\"\n" +
                        "                    >\n" +
                        "\n" +
                        "                    <TextView\n" +
                        "                        android:layout_width=\"match_parent\"\n" +
                        "                        android:layout_height=\"wrap_content\"\n" +
                        "                        android:text=\"货物信息\"\n" +
                        "                        android:id=\"@+id/staffinfo\"\n" +
                        "                        />\n" +
                        "                    <com.getbase.floatingactionbutton.AddFloatingActionButton\n" +
                        "                        android:id=\"@+id/addStaff\"\n" +
                        "                        android:layout_alignParentRight=\"true\"\n" +
                        "                        android:layout_width=\"wrap_content\"\n" +
                        "                        android:layout_height=\"wrap_content\"\n" +
                        "                        fab:fab_plusIconColor=\"@color/half_black\"\n" +
                        "                        fab:fab_colorNormal=\"@color/white\"\n" +
                        "                        fab:fab_colorPressed=\"@color/white_pressed\"\n" +
                        "                        android:layout_marginBottom=\"16dp\"\n" +
                        "                        android:maxWidth=\"16dp\"\n" +
                        "                        android:maxHeight=\"16dp\"\n" +
                        "                        android:layout_marginStart=\"16dp\"/>\n" +
                        "                </RelativeLayout>\n" +
                        "\n" +
                        "                <LinearLayout style=\"@style/common_horizontal_division_line_style\"></LinearLayout>\n" +
                        "\n" +
                        "            </LinearLayout>\n" +
                        "\n" +
                        "            <LinearLayout\n" +
                        "                android:orientation=\"vertical\"\n" +
                        "                android:layout_width=\"match_parent\"\n" +
                        "                android:layout_height=\"match_parent\"\n" +
                        "                android:layout_below=\"@id/upper\"\n" +
                        "                android:id=\"@+id/mid\">\n" +
                        "\n" +
                        "                <LinearLayout\n" +
                        "                    android:layout_width=\"match_parent\"\n" +
                        "                    android:layout_height=\"wrap_content\"\n" +
                        "                    android:orientation=\"vertical\">\n");

            else if (i == 4) {
                System.out.print("            </LinearLayout>\n");
                System.out.print("        </LinearLayout>\n");
                System.out.print(" <LinearLayout\n" +
                        "                android:orientation=\"vertical\"\n" +
                        "                android:layout_width=\"match_parent\"\n" +
                        "                android:layout_height=\"wrap_content\"\n" +
                        "                android:layout_below=\"@id/mid\">\n" +
                        "\n" +
                        "                <TextView\n" +
                        "                    android:layout_width=\"match_parent\"\n" +
                        "                    android:layout_height=\"wrap_content\"\n" +
                        "                    android:text=\"价格信息\" />\n" +
                        "\n" +
                        "                <LinearLayout style=\"@style/common_horizontal_division_line_style\"></LinearLayout>");
            } else
                printCg(i);
            if (i == 0) {
                for (int j = 0; j < 2; j++) {
                    printList(i, j * 2);
                    printEdit(i, j * 2 + 1);
                }
                printEdit(i,4);
                continue;
            }
            for (int j = 0; j < ATTRIBUTE_NAMES[i].length; j++) {
                Boolean flag = false;
                if (list[i] != null) {
                    for (int k = 0; k < list[i].length; k++) {
                        if (list[i][k] == j)
                            flag = true;
                    }
                }
                if (flag)
                    printList(i, j);
                else
                    printEdit(i, j);
                if ((i == 4 && j%2==1 )|| (i == 3 && j%2==1))
                    j++;

            }

        }
        System.out.print("            </LinearLayout>\n" + "        </RelativeLayout>\n" + "    </ScrollView>");
    }

    public static void printList(int i, int j) {
        if (i == 4 && j%2==1 || (i == 3 && j%2==1)) {
            System.out.println("<LinearLayout\r\n" + "android:layout_width=\"match_parent\"\n"
                    + "android:layout_height=\"wrap_content\"\n" + "android:orientation=\"horizontal\">\n");
            printSpinner(i, j);
            if (j + 1 < ATTRIBUTE_NAMES[i].length)
                printSpinner(i, j + 1);
            System.out.println("</LinearLayout>\r\n");
        } else {
            printSpinner(i, j);
        }
    }

    public static void printSpinner(int i, int j) {
        System.out.println("<LinearLayout\r\n" + "            android:layout_width=\"wrap_content\"\r\n"
                + "            android:layout_height=\"wrap_content\"\r\n"
                + "            android:orientation=\"horizontal\">\r\n" + "            <TextView\r\n"
                + "                android:layout_width=\"wrap_content\"\r\n"
                + "                android:layout_height=\"wrap_content\"\r\n"
                + "                android:layout_weight=\"1\"\r\n" + "                android:text=\""
                + ATTRIBUTE_NAMES[i][j] + "\"/>\r\n" + "            <Spinner\r\n" + "                android:id=\"@+id/"
                + ATTRIBUTE_VALUES[i][j] + "\"\r\n" + "                android:layout_width=\"match_parent\"\r\n"
                + "                android:layout_height=\"wrap_content\"\r\n"
                + "				android:entries=\"@array/" + ATTRIBUTE_VALUES[i][j] + "\""
                + "                android:layout_weight=\"1\">\r\n" + "\r\n" + "            </Spinner>\r\n"
                + "        </LinearLayout>");
    }

    public static void printCg(int i) {
        System.out.println("<TextView\n" + "android:layout_width=\"match_parent\"\n"
                + "android:layout_height=\"wrap_content\"\n" + "android:text=\"" + INFO[i] + "\"/>\n");
        System.out.println(" <LinearLayout style=\"@style/common_horizontal_division_line_style\"></LinearLayout>");
    }

    public static void printEdit(int i, int j) {
        if ((i == 3&&j%2==1) ||( i == 4 && j % 2 == 1)) {
            System.out.println("<LinearLayout\r\n" + "android:layout_width=\"match_parent\"\n"
                    + "android:layout_height=\"wrap_content\"\n" + "android:orientation=\"horizontal\">\n");
            System.out.println(" <com.wrapp.floatlabelededittext.FloatLabeledEditText\n"
                    + "android:layout_width=\"wrap_content\"\r\n" + "android:layout_weight=\"1\"\n"
                    + "android:layout_height=\"wrap_content\">\n" + "<EditText\n" + "android:id=\"@+id/"
                    + ATTRIBUTE_VALUES[i][j] + "\"\n" + "android:layout_width=\"match_parent\"\n"
                    + "android:layout_height=\"wrap_content\"\n" + "android:singleLine=\"true\"\n" + "android:hint=\""
                    + ATTRIBUTE_NAMES[i][j] + "\"/>\n" + "</com.wrapp.floatlabelededittext.FloatLabeledEditText>\n");
            if (j + 1 < ATTRIBUTE_NAMES[i].length)
                System.out.println(" <com.wrapp.floatlabelededittext.FloatLabeledEditText\n"
                        + "android:layout_width=\"wrap_content\"\r\n" + "android:layout_weight=\"1\"\n"
                        + "android:layout_height=\"wrap_content\">\n" + "<EditText\n" + "android:id=\"@+id/"
                        + ATTRIBUTE_VALUES[i][j + 1] + "\"\n" + "android:layout_width=\"match_parent\"\n"
                        + "android:layout_height=\"wrap_content\"\n" + "android:singleLine=\"true\"\n"
                        + "android:hint=\"" + ATTRIBUTE_NAMES[i][j + 1] + "\"/>\n"
                        + "</com.wrapp.floatlabelededittext.FloatLabeledEditText>\n");
            System.out.println("</LinearLayout>\r\n");
        } else {
            System.out.println(" <com.wrapp.floatlabelededittext.FloatLabeledEditText\n"
                    + "android:layout_width=\"match_parent\"\n" + "android:layout_height=\"wrap_content\">\n"
                    + "<EditText\n" + "android:id=\"@+id/" + ATTRIBUTE_VALUES[i][j] + "\"\n"
                    + "android:layout_width=\"match_parent\"\n" + "android:layout_height=\"wrap_content\"\n"
                    + "android:singleLine=\"true\"\n" + "android:hint=\"" + ATTRIBUTE_NAMES[i][j] + "\"/>\n"
                    + "</com.wrapp.floatlabelededittext.FloatLabeledEditText>\n");
        }
    }
}
