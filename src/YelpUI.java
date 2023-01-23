import com.yelp.supportclasses.*;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.security.auth.Subject;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class YelpUI extends JFrame {
    private JButton execute_query_bt;
    private JPanel business_panel;
    private JComboBox and_or_main_cmbox;
    private JLabel search_for_lb;
    private JPanel user_panel;
    private JLabel users_tv;
    private JComboBox member_since_cbx;
    private JComboBox review_count_cbx;
    private JComboBox no_of_friends_cbx;
    private JComboBox avg_stars_cbx;
    private JComboBox no_of_votes_cbx;
    private JLabel member_since_lb;
    private JLabel review_count_lb;
    private JLabel no_of_friends_lb;
    private JLabel avg_stars_lb;
    private JLabel no_of_votes_lb;
    private JComboBox review_star_cbx;
    private JComboBox review_votes_cbx;
    private JTextField review_count_value;
    private JTextField no_of_friends_value;
    private JTextField avg_stars_value;
    private JTextField no_of_votes_value;
    private JPanel mainPanel;
    private JComboBox user_and_or_cbx;
    private JLabel users_and_or_label;
    private JButton business_q_bt;
    private JButton user_q_bt;
    private JTextField review_from_txbx;
    private JTextField review_to_txbx;
    private JTextField member_since_txbx;
    private JButton get_sub_cat_bt;
    private JButton get_attr_bt;
    private JPanel mainCategoryJPanel;
    private JScrollPane mainCatJScrollPan;
    private JScrollPane subCatJScrollPan;
    private JScrollPane attrJScrollPAn;
    private JScrollPane resultsJScrollPane;
    private JTextField review_star_value;
    private JTextField review_votes_value;
    private JPanel Reviews2;

    //My vars
    UIDataMirror uiDataMirror;
    BusinessClass businessClass;
    UserClass userClass;
    ReviewsClass reviewsClass;

    //Connection vars
    Connection dbConnection = null;
    boolean CONNECTION_ESTABLISHED = false;

    //UI related vars
    Set<String> selectedMainCategories = new HashSet<>();
    Set<String> selectedSubCategories = new HashSet<>();
    Set<String> selectedAttributes = new HashSet<>();

    JPanel mainCatJpanel = new JPanel();
    JPanel subCatJpanel = new JPanel();
    JPanel attrsJpanel = new JPanel();

    JTable resultsTAble;
    ArrayList<BusinessTableEntry> btableData = new ArrayList<>();
    ArrayList<BreviewsTableEntry> rtableData = new ArrayList<>();
    ArrayList<UserTableEntry> utableData = new ArrayList<>();

    Statement statement;

    public YelpUI() {
        setTitle("Yelp");
        setContentPane(mainPanel);
        setSize(850, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        business_q_bt.setEnabled(false);

        businessClass = new BusinessClass();
        userClass = new UserClass();
        reviewsClass = new ReviewsClass();
        initComponents();

        try {
            dbConnection = DBConnection.getDBConnection();
            statement = dbConnection.createStatement();
            CONNECTION_ESTABLISHED = true;
            System.out.println("Established connection with database");
        } catch (Exception e) {
            System.out.println("Establishing db connection failed : " + e.getMessage());
        }

        mainCatJpanel.setLayout(new BoxLayout(mainCatJpanel, BoxLayout.Y_AXIS));
        subCatJpanel.setLayout(new BoxLayout(subCatJpanel, BoxLayout.Y_AXIS));
        attrsJpanel.setLayout(new BoxLayout(attrsJpanel, BoxLayout.Y_AXIS));

    }

    void initComponents() {

        review_to_txbx.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (review_to_txbx.getText().trim().length() > 0) {
                    reviewsClass.setTo_date(review_to_txbx.getText());
                }
                else
                {
                    reviewsClass.setTo_date(null);
                }
            }
        });
        review_from_txbx.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (review_from_txbx.getText().trim().length() > 0) {
                    reviewsClass.setFrom_date(review_from_txbx.getText());
                }
                else
                {
                    reviewsClass.setFrom_date(null);
                }
            }
        });
        review_star_cbx.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    reviewsClass.setStar_operator((String) review_star_cbx.getSelectedItem());
                }
            }
        });
        review_votes_cbx.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    reviewsClass.setVotes_operator((String) review_votes_cbx.getSelectedItem());
                }
            }
        });
        review_star_value.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (review_star_value.getText().trim().length() > 0) {
                    reviewsClass.setStar_value(review_star_value.getText());
                }
                else
                {
                    reviewsClass.setStar_value(null);
                }
            }
        });
        review_votes_value.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (review_votes_value.getText().trim().length() > 0) {
                    reviewsClass.setVotes_value(review_votes_value.getText());
                }
                else
                {
                    reviewsClass.setVotes_value(null);
                }
            }
        });

        member_since_txbx.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (member_since_txbx.getText().trim().length() > 0) {
                    userClass.setMember_since(member_since_txbx.getText());
                }
                else
                {
                    userClass.setMember_since(null);
                }
            }
        });
        review_count_value.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (review_count_value.getText().trim().length() > 0) {
                    userClass.setReview_count(review_count_value.getText());
                }
                else
                {
                    userClass.setReview_count(null);
                }
            }
        });
        no_of_friends_value.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (no_of_friends_value.getText().trim().length() > 0) {
                    userClass.setNo_of_friends(no_of_friends_value.getText());
                }
                else
                {
                    userClass.setNo_of_friends(null);
                }
            }
        });
        avg_stars_value.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (avg_stars_value.getText().trim().length() > 0) {
                    userClass.setAvg_stars(avg_stars_value.getText());
                }
                else
                {
                    userClass.setAvg_stars(null);
                }
            }
        });
        no_of_votes_value.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (no_of_votes_value.getText().trim().length() > 0) {
                    userClass.setNo_of_votes(no_of_votes_value.getText());
                }
                else
                {
                    userClass.setNo_of_votes(null);
                }
            }
        });

        member_since_cbx.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    userClass.setMember_since_operator((String) member_since_cbx.getSelectedItem());
                }
            }
        });
        review_count_cbx.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    userClass.setReview_count_operator((String) review_count_cbx.getSelectedItem());
                }
            }
        });
        no_of_friends_cbx.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    userClass.setNo_of_friends_operator((String) no_of_friends_cbx.getSelectedItem());
                }
            }
        });
        avg_stars_cbx.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    userClass.setAvg_stars_operator((String) avg_stars_cbx.getSelectedItem());
                }
            }
        });
        no_of_votes_cbx.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    userClass.setNo_of_votes_operator((String) no_of_votes_cbx.getSelectedItem());
                }
            }
        });

        business_q_bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                businessClass.setSelectedMainCategories(selectedMainCategories);
                businessClass.setSelectedSubCategories(selectedSubCategories);
                businessClass.setSelectedAttributes(selectedAttributes);
                uiDataMirror = new UIDataMirror(businessClass, userClass, reviewsClass);
                try {
                    buildBusinessSearchQUery1();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });
        user_q_bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uiDataMirror = new UIDataMirror(businessClass, userClass, reviewsClass);
                try{
                    buildUserSearchQuery();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        and_or_main_cmbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    businessClass.setAnd_Or_main((String) and_or_main_cmbox.getSelectedItem());
                }
            }
        });
        user_and_or_cbx.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    userClass.setUser_and_or_opeartor((String) user_and_or_cbx.getSelectedItem());
                }
            }
        });

    }

    //   [[----------Load Business categories----------]]
    // Load main categories
    private void initMainCategoryUI() throws SQLException {

        String fetchMainCatQuery = "select * from BUSINESS_MAIN_CATEGORIES";
        ResultSet resultSet = statement.executeQuery(fetchMainCatQuery);

        while (resultSet.next()) {
            JCheckBox mainCatCbox = new JCheckBox(resultSet.getString(1));
            mainCatJpanel.add(mainCatCbox);
            mainCatCbox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (mainCatCbox.isSelected()) {
                        selectedMainCategories.add(mainCatCbox.getText());
                    } else {
                        selectedMainCategories.remove(mainCatCbox.getText());
                    }

                    if(selectedMainCategories.size() > 0)
                        business_q_bt.setEnabled(true);
                    else
                        business_q_bt.setEnabled(false);

                    try{
                        updateSubCategoriesUI();
                    }
                    catch (SQLException sqle)
                    {
                        sqle.printStackTrace();
                    }
                }
            });

        }
        mainCatJScrollPan.setViewportView(mainCatJpanel);
        mainCatJScrollPan.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainCatJScrollPan.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }

    // Load subcategories
    private void updateSubCategoriesUI() throws SQLException
    {
        //    select DISTINCT (cat.BUSINESS_CATEGORY_NAME)
        //    from BUSINESS_CATEGORY cat
        //    where (cat.BUSINESS_CATEGORY_NAME NOT IN (select bcm.CATEGORY_NAME from BUSINESS_MAIN_CATEGORIES bcm))
        //    AND (cat.BUSINESS_ID IN (select cat.BUSINESS_ID from BUSINESS_CATEGORY cat where cat.BUSINESS_CATEGORY_NAME = 'Active Life'))
        //    order by cat.BUSINESS_CATEGORY_NAME;

        String fetchSubCatQuery = "select DISTINCT (cat.BUSINESS_CATEGORY_NAME) from BUSINESS_CATEGORY cat " +
                "where (cat.BUSINESS_CATEGORY_NAME NOT IN (select bcm.CATEGORY_NAME from BUSINESS_MAIN_CATEGORIES bcm)) " +
                "AND (cat.BUSINESS_ID IN (select cat.BUSINESS_ID from BUSINESS_CATEGORY cat where cat.BUSINESS_CATEGORY_NAME = ";
        String fetchSubCatQuery_2 = ")) " +
                " order by cat.BUSINESS_CATEGORY_NAME;";
        String selectedCatString ="";

        subCatJpanel.removeAll();
        selectedSubCategories.clear();

        for(String str : selectedMainCategories)
        {
            selectedCatString = str;
            ResultSet resultSet = statement.executeQuery( fetchSubCatQuery+"'"+selectedCatString+"'"+fetchSubCatQuery_2);

            while (resultSet.next()) {
                JCheckBox subCatCBox = new JCheckBox(resultSet.getString(1));
                subCatJpanel.add(subCatCBox);
                subCatCBox.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        if (subCatCBox.isSelected()) {
                            selectedSubCategories.add(subCatCBox.getText());
                        }
                        else {
                            selectedSubCategories.remove(subCatCBox.getText());
                        }
                        try{
                            updateAttributesUI();
                        }
                        catch (SQLException sqle)
                        {
                            sqle.printStackTrace();
                        }
                    }
                });
            }
        }
        subCatJScrollPan.setViewportView(subCatJpanel);
        subCatJScrollPan.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        subCatJScrollPan.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        attrsJpanel.removeAll();
        selectedAttributes.clear();
        attrJScrollPAn.setViewportView(attrsJpanel);
    }

    // Load attributes
    private void updateAttributesUI() throws SQLException
    {
        //    select DISTINCT(ba.B_ATTRIBUTE)
        //    from BUSINESS_ATTRIBUTES ba
        //    where ba.BUSINESS_ID IN (select cat.BUSINESS_ID
        //            from BUSINESS_CATEGORY cat
        //            where cat.BUSINESS_CATEGORY_NAME = 'Active Life' OR
        //            cat.BUSINESS_CATEGORY_NAME = 'Fitness & Instruction' OR
        //            cat.BUSINESS_CATEGORY_NAME = 'Health & Medical'
        //            group by cat.BUSINESS_ID
        //            having COUNT(cat.BUSINESS_ID) = 3);

        selectedAttributes.clear();
        attrsJpanel.removeAll();

        String fetchAttrCatQuery = "select DISTINCT(ba.B_ATTRIBUTE) " +
                "from BUSINESS_ATTRIBUTES ba " +
                "where ba.BUSINESS_ID IN (select cat.BUSINESS_ID from BUSINESS_CATEGORY cat where ";
        String fetchAttrCatQuery_2 = " group by cat.BUSINESS_ID having COUNT(cat.BUSINESS_ID) = ";
        String fetchAttrCatQuery_3 = ") " +
                "order by ba.B_ATTRIBUTE;";
        StringBuilder selectedAttrString_1 = new StringBuilder();
        String selectedCatString_2 = Integer.toString(selectedSubCategories.size()+selectedMainCategories.size());
        String prefixStr = "cat.BUSINESS_CATEGORY_NAME = ";
        boolean isItTheFirstAttrINTable = false;

            for(String str : selectedSubCategories)
            {
                if (isItTheFirstAttrINTable)
                    selectedAttrString_1.append(" OR ").append(prefixStr).append("'").append(str).append("'");
                else
                    selectedAttrString_1 = new StringBuilder(prefixStr+"'"+str+"'");

                isItTheFirstAttrINTable = true;

                for(String str1 : selectedMainCategories)
                {
                    if (isItTheFirstAttrINTable)
                        selectedAttrString_1.append(" OR ").append(prefixStr).append("'").append(str1).append("'");
                    else
                        selectedAttrString_1 = new StringBuilder(prefixStr+"'"+str1+"'");

                    isItTheFirstAttrINTable = true;
                }
                ResultSet resultSet = statement.executeQuery( fetchAttrCatQuery + selectedAttrString_1 + fetchAttrCatQuery_2 +
                        selectedCatString_2 + fetchAttrCatQuery_3);

                while (resultSet.next()) {
                    JCheckBox attrCBox = new JCheckBox(resultSet.getString(1));
                    attrsJpanel.add(attrCBox);
                    attrCBox.addItemListener(new ItemListener() {
                        @Override
                        public void itemStateChanged(ItemEvent e) {
                            if (attrCBox.isSelected()) {
                                selectedAttributes.add(attrCBox.getText());
                            }
                            else {
                                selectedAttributes.remove(attrCBox.getText());
                            }
                        }
                    });
                }
            }
        attrJScrollPAn.setViewportView(attrsJpanel);
        attrJScrollPAn.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        attrJScrollPAn.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }



    //   [[------Business Search Query------]]
    void buildBusinessSearchQUery1() throws SQLException
    {
        StringBuilder businessQuery = new StringBuilder();
        StringBuilder attributesSubString = new StringBuilder();
        String attrsCount = " >= 0";
        String attributesWholeStr = "";
        String attrPrefix = "ba.B_ATTRIBUTE = ";
        StringBuilder subCatSubString = new StringBuilder();
        String subCatsCount = " >= 0";
        String subCatWholeStr = "";
        String subCatPrefix = "cat2.BUSINESS_CATEGORY_NAME = ";
        StringBuilder mainCatSubStr = new StringBuilder();
        String mainCatsCount = " >=0 ";
        String mainCatWholeStr = "";
        String mainCatPrefix = "cat.BUSINESS_CATEGORY_NAME = ";

//        cat2.BUSINESS_ID IN (select ba.BUSINESS_ID
//        from BUSINESS_ATTRIBUTES ba
//        where ba.B_ATTRIBUTE = 'Accepts Credit Cards true'
//        OR ba.B_ATTRIBUTE = 'Accepts Insurance false'
//        OR ba.B_ATTRIBUTE = 'By Appointment Only false'
//        group by ba.BUSINESS_ID
//        having COUNT(ba.BUSINESS_ID) >= 0)
//        AND
        if(selectedAttributes.size() > 0)
        {
            for(String temp : selectedAttributes){
                if(attributesSubString.length() == 0)
                    attributesSubString = new StringBuilder(attrPrefix + "'"+temp+"'"+ " ");
                else
                    attributesSubString.append(" OR ").append(attrPrefix).append("'").append(temp).append("'").append(" ");
            }

            if(businessClass.getAnd_Or_main() == "AND")
                attrsCount = " = "+Integer.toString(selectedAttributes.size());

            attributesWholeStr = "        cat2.BUSINESS_ID IN (select ba.BUSINESS_ID\n" +
                    "        from BUSINESS_ATTRIBUTES ba\n" +
                    "        where "+attributesSubString+
                    "        group by ba.BUSINESS_ID\n" +
                    "        having COUNT(ba.BUSINESS_ID)"+ attrsCount + ")\n" +
                    "        AND ";
        }

//        (cat.BUSINESS_ID IN (select cat2.BUSINESS_ID
//                from BUSINESS_CATEGORY cat2
//                where [attributesWholeStr] (cat2.BUSINESS_CATEGORY_NAME = 'Yoga'
//                        OR cat2.BUSINESS_CATEGORY_NAME = 'Pilates'
//                        OR cat2.BUSINESS_CATEGORY_NAME = 'Weight Loss Centers')
//
//                group by cat2.BUSINESS_ID
//                having COUNT(cat2.BUSINESS_ID) >= 0))
//        AND
        if(selectedSubCategories.size() > 0)
        {
            for(String temp : selectedSubCategories) {
                if(subCatSubString.length() == 0)
                    subCatSubString = new StringBuilder(subCatPrefix + "'"+temp+"'"+ " ");
                else
                    subCatSubString.append(" OR ").append(subCatPrefix).append("'").append(temp).append("'").append(" ");
            }

            if(businessClass.getAnd_Or_main() == "AND")
                subCatsCount = " = "+Integer.toString(selectedSubCategories.size());

            subCatWholeStr = "    AND    (cat.BUSINESS_ID IN (select cat2.BUSINESS_ID\n" +
                    "                from BUSINESS_CATEGORY cat2\n" +
                    "                where "+ attributesWholeStr + subCatSubString+
                    "                group by cat2.BUSINESS_ID\n" +
                    "                having COUNT(cat2.BUSINESS_ID) "+ subCatsCount +"))\n" +
                    "        ";
        }

//        (select b.BUSINESS_ID, b.BUSINESS_NAME, b.CITY, b.STATE, b.STARS
//        from BUSINESS_CATEGORY cat, BUSINESS b
//        where (cat.BUSINESS_ID = b.BUSINESS_ID)
//        AND [subCatWholeStr]
//        AND
//                ( cat.BUSINESS_CATEGORY_NAME = 'Active Life'
//                        OR cat.BUSINESS_CATEGORY_NAME = 'Fitness & Instruction'
//                        OR cat.BUSINESS_CATEGORY_NAME = 'Health & Medical' )
//        group by cat.BUSINESS_ID
//        having COUNT(cat.BUSINESS_ID) >= 0);
        for(String temp : selectedMainCategories)
        {
            if(mainCatSubStr.length() == 0)
                mainCatSubStr = new StringBuilder(mainCatPrefix + "'"+temp+"'"+ " ");
            else
                mainCatSubStr.append(" OR ").append(mainCatPrefix).append("'").append(temp).append("'").append(" ");
        }

        if(businessClass.getAnd_Or_main() == "AND")
            mainCatsCount = " = "+ Integer.toString(selectedMainCategories.size());

        mainCatWholeStr = "        (select b.BUSINESS_ID, b.BUSINESS_NAME, b.CITY, b.STATE, b.STARS\n" +
                "        from BUSINESS_CATEGORY cat, BUSINESS b\n" +
                "        where (cat.BUSINESS_ID = b.BUSINESS_ID)\n" +
                "         " + subCatWholeStr+
                "        AND\n (" + mainCatSubStr+ ")"+
                "        group by cat.BUSINESS_ID\n" +
                "        having COUNT(cat.BUSINESS_ID) "+ mainCatsCount +");";


        businessQuery = new StringBuilder(mainCatWholeStr);
        ResultSet resultSet = statement.executeQuery(businessQuery.toString());
        btableData.clear();
        while (resultSet.next())
        {
            btableData.add(new BusinessTableEntry(resultSet.getString(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4), resultSet.getString(5)));
        }

        String[] columns = {"NAME", "CITY", "STATE", "STARS"};
        String[][] rowsData = new String[btableData.size()][4];

        for(int i = 0; i<btableData.size(); i++)
        {
            BusinessTableEntry obj = btableData.get(i);
            rowsData[i][0] = obj.businessName;
            rowsData[i][1] = obj.businessCity;
            rowsData[i][2] = obj.businessState;
            rowsData[i][3] = obj.businessStars;
        }

        resultsTAble = new JTable(rowsData, columns) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        } ;
        resultsJScrollPane.setViewportView(resultsTAble);
        resultsJScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        resultsJScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        resultsTAble.setFocusable(false);
        resultsTAble.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {     // to detect double click events
                    JTable target = (JTable)me.getSource();
                    int row = target.getSelectedRow();
                    String selectedBusinessID = btableData.get(row).businessID;
                    String selectedBuinessName = btableData.get(row).businessName;
                    //Show all reviews of this business
                    try {
                        showAllReviewsOfThisBusiness(selectedBusinessID, selectedBuinessName);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    void showAllReviewsOfThisBusiness(String selectedBusinessID, String selectedBuinessName) throws SQLException
    {
        //--------[[SQL query]]---------//
//        select r.REVIEW_ID, r.DATEE, r.REVIEW_TEXT, yu.USER_NAME,r.STARS, r.COOL_VOTES, r.FUNNY_VOTES, r.USEFUL_VOTES
//        from REVIEWS r, YELP_USERS yu
//        where yu.USER_ID = r.USER_ID AND
//        r.BUSINESS_ID = 'aEoLasF389EAlRn0eTkuBA';

//        select r.REVIEW_ID, r.DATEE, r.REVIEW_TEXT, yu.USER_NAME,r.STARS, r.COOL_VOTES, r.FUNNY_VOTES, r.USEFUL_VOTES
//        from REVIEWS r, YELP_USERS yu
//        where yu.USER_ID = r.USER_ID AND
//                r.BUSINESS_ID = 'aEoLasF389EAlRn0eTkuBA'
//                AND r.STARS > 0
//                AND r.TOTAL_VOTES > 0
//                AND r.DATEE > '2021-02-21'
//                AND r.DATEE < '2021-12-31'
//            ;
        StringBuilder reviewsQuery = new StringBuilder();
        StringBuilder reviewProperties = new StringBuilder();

        if(reviewsClass.getStar_value() != null)
            if(reviewsClass.getStar_value().length() > 0)
        {
            if(reviewProperties.length() > 0 )
                reviewProperties.append(" AND r.STARS ").append(reviewsClass.getStar_operator()).append(" ").append(reviewsClass.getStar_value());
            else
                reviewProperties = new StringBuilder(" AND r.STARS " + reviewsClass.getStar_operator() + " "+reviewsClass.getStar_value());
        }
        if(reviewsClass.getVotes_value() != null)
            if(reviewsClass.getVotes_value().length() > 0)
        {
            if(reviewProperties.length() > 0 )
                reviewProperties.append(" AND r.TOTAL_VOTES ").append(reviewsClass.getVotes_operator()).append(" ").append(reviewsClass.getVotes_value());
            else
                reviewProperties = new StringBuilder(" AND r.TOTAL_VOTES " + reviewsClass.getVotes_operator() + " " + reviewsClass.getVotes_value());
        }
        if(reviewsClass.getFrom_date() != null)
            if(reviewsClass.getFrom_date().length() == 10)
        {
            if(reviewProperties.length() > 0 )
                reviewProperties.append(" AND r.DATEE >= ").append("'").append(reviewsClass.getFrom_date()).append("'");
            else
                reviewProperties = new StringBuilder(" AND r.DATEE >= " + "'" + reviewsClass.getFrom_date() + "'");
        }
        if(reviewsClass.getTo_date() != null)
            if(reviewsClass.getTo_date().length() == 10)
        {
            if(reviewProperties.length() > 0 )
                reviewProperties.append(" AND r.DATEE <= ").append("'").append(reviewsClass.getTo_date()).append("'");
            else
                reviewProperties = new StringBuilder(" AND r.DATEE <= " + "'" + reviewsClass.getTo_date() + "'");
        }

        if(reviewProperties.length() > 0)
        {
             reviewsQuery = new StringBuilder("select r.REVIEW_ID, r.DATEE, r.REVIEW_TEXT, yu.USER_NAME,r.STARS, r.COOL_VOTES, r.FUNNY_VOTES, r.USEFUL_VOTES " +
                    " from REVIEWS r, YELP_USERS yu " +
                    " where yu.USER_ID = r.USER_ID AND " +
                    " r.BUSINESS_ID = '"+ selectedBusinessID + "'" +reviewProperties +";");
        }
        else
        {
             reviewsQuery = new StringBuilder("select r.REVIEW_ID, r.DATEE, r.REVIEW_TEXT, yu.USER_NAME,r.STARS, r.COOL_VOTES, r.FUNNY_VOTES, r.USEFUL_VOTES " +
                    " from REVIEWS r, YELP_USERS yu " +
                    " where yu.USER_ID = r.USER_ID AND " +
                    " r.BUSINESS_ID = '"+ selectedBusinessID +"';");
        }
        ResultSet resultSet = statement.executeQuery(reviewsQuery.toString());
        rtableData.clear();

        //r.REVIEW_ID, r.DATEE, r.REVIEW_TEXT, yu.USER_NAME,r.STARS, r.COOL_VOTES, r.FUNNY_VOTES, r.USEFUL_VOTES
        while (resultSet.next())
        {
            rtableData.add(new BreviewsTableEntry(resultSet.getString(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4),
                    resultSet.getString(5), resultSet.getString(6),
                    resultSet.getString(7), resultSet.getString(8)));
        }

        String[] columns = {"DATE", "TEXT", "USER NAME", "STARS", "COOL VOTES", "FUNNY VOTES", "USEFUL VOTES"};
        String[][] rowsData = new String[rtableData.size()][7];

        for(int i = 0; i<rtableData.size(); i++)
        {
            BreviewsTableEntry obj = rtableData.get(i);
            rowsData[i][0] = obj.datee;
            rowsData[i][1] = obj.reviewText;
            rowsData[i][2] = obj.userName;
            rowsData[i][3] = obj.stars;
            rowsData[i][4] = obj.coolVotes;
            rowsData[i][5] = obj.funnyVotes;
            rowsData[i][6] = obj.usefulVotes;

        }

        //------------[[Open new JFrame with table and populate it]]----------------//
        NewFrame nf = new NewFrame();
        nf.makeAndDisplayTable(columns, rowsData, selectedBuinessName);
    }

    //   [[--------User Search Query---------]]
    void buildUserSearchQuery() throws SQLException
    {
        //    select yu.USER_ID, yu.USER_NAME, yu.YELPING_SINCE, yu.AVERAGE_STARS
        //    from YELP_USERS yu
        //    where yu.YELPING_SINCE >= '2007-02-01'
        //    AND yu.REVIEW_COUNT > 0
        //    AND yu.NO_OF_FRIENDS > 0
        //    AND yu.AVERAGE_STARS > 0
        //    AND yu.NO_OF_VOTES > 0
        //    ;

        StringBuilder userQueryConditions = new StringBuilder();
        StringBuilder userQuery = new StringBuilder();

        if(userClass.getMember_since() != null)
            if(userClass.getMember_since().length() > 0)
            {
                userQueryConditions = new StringBuilder(" where "+ "yu.YELPING_SINCE >= "+ "'"+userClass.getMember_since()+"' ");
            }

        if(userClass.getReview_count() != null)
            if(userClass.getReview_count().length() > 0)
            {
                if(userQueryConditions.length() == 0)
                {
                    userQueryConditions = new StringBuilder(" where "+ "yu.REVIEW_COUNT "+
                            userClass.getReview_count_operator()+ " "+ userClass.getReview_count());
                }
                else
                {
                    userQueryConditions.append(" ").append(userClass.getUser_and_or_opeartor()).append(" ").append("yu.REVIEW_COUNT")
                            .append(" ").append(userClass.getReview_count_operator()).append(" ").append(userClass.getReview_count());
                }
            }

        if(userClass.getNo_of_friends() != null)
            if(userClass.getNo_of_friends().length() > 0)
            {
                if(userQueryConditions.length() == 0)
                {
                    userQueryConditions = new StringBuilder(" where "+ "yu.NO_OF_FRIENDS "+
                            userClass.getNo_of_friends_operator()+ " "+ userClass.getNo_of_friends());
                }
                else
                {
                    userQueryConditions.append(" ").append(userClass.getUser_and_or_opeartor()).append(" ").append("yu.NO_OF_FRIENDS")
                            .append(" ").append(userClass.getNo_of_friends_operator()).append(" ").append(userClass.getNo_of_friends());
                }
            }

        if(userClass.getAvg_stars() != null)
            if(userClass.getAvg_stars().length() > 0)
            {
                if(userQueryConditions.length() == 0)
                {
                    userQueryConditions = new StringBuilder(" where "+ "yu.AVERAGE_STARS "+
                            userClass.getAvg_stars_operator()+ " "+ userClass.getAvg_stars());
                }
                else
                {
                    userQueryConditions.append(" ").append(userClass.getUser_and_or_opeartor()).append(" ").append("yu.AVERAGE_STARS")
                            .append(" ").append(userClass.getAvg_stars_operator()).append(" ").append(userClass.getAvg_stars());
                }
            }

        if(userClass.getNo_of_votes() != null)
            if(userClass.getNo_of_votes().length() > 0)
            {
                if(userQueryConditions.length() == 0)
                {
                    userQueryConditions = new StringBuilder(" where "+ "yu.NO_OF_VOTES "+
                            userClass.getNo_of_votes_operator()+ " "+ userClass.getNo_of_friends());
                }
                else
                {
                    userQueryConditions.append(" ").append(userClass.getUser_and_or_opeartor()).append(" ").append("yu.NO_OF_VOTES")
                            .append(" ").append(userClass.getNo_of_votes_operator()).append(" ").append(userClass.getNo_of_votes());
                }
            }

        //order by yu.USER_NAME
        if(userQueryConditions.length() > 0)
        {
             userQuery = new StringBuilder("select yu.USER_ID, yu.USER_NAME, yu.YELPING_SINCE, yu.AVERAGE_STARS" +
                    " from YELP_USERS yu" + userQueryConditions+
                    " ;");
        }
        else
        {
             userQuery = new StringBuilder("select yu.USER_ID, yu.USER_NAME, yu.YELPING_SINCE, yu.AVERAGE_STARS" +
                    " from YELP_USERS yu" +
                    " ;");
        }

        ResultSet resultSet = statement.executeQuery(userQuery.toString());

        utableData.clear();
        //yu.USER_ID, yu.USER_NAME, yu.YELPING_SINCE, yu.AVERAGE_STARS
        while (resultSet.next())
        {
            utableData.add(new UserTableEntry(resultSet.getString(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4)));
        }

        String[] columns = {"NAME", "YELPING SINCE", "AVERAGE STAR"};
        String[][] rowsData = new String[utableData.size()][3];

        for(int i = 0; i<utableData.size(); i++)
        {
            UserTableEntry obj = utableData.get(i);
            rowsData[i][0] = obj.userName;
            rowsData[i][1] = obj.yelpingSince;
            rowsData[i][2] = obj.averageStars;
        }

        resultsTAble = new JTable(rowsData, columns) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        } ;
        resultsJScrollPane.setViewportView(resultsTAble);
        resultsJScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        resultsJScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        resultsTAble.setFocusable(false);
        resultsTAble.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {     // to detect double click events
                    JTable target = (JTable)me.getSource();
                    int row = target.getSelectedRow();

                    String selectedUserID = utableData.get(row).userID;
                    String selectedUserName = utableData.get(row).userName;

                    try {
                        showAllReviewsOfThisUser(selectedUserID, selectedUserName);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    void showAllReviewsOfThisUser(String selectedUserID, String selectedUserName) throws SQLException
    {
        //--------[[SQL query]]---------//
//        select r.REVIEW_ID, r.DATEE, r.REVIEW_TEXT, b.BUSINESS_NAME, r.STARS, r.COOL_VOTES, r.FUNNY_VOTES, r.USEFUL_VOTES
//        from REVIEWS r, BUSINESS b
//        where r.BUSINESS_ID = b.BUSINESS_ID
//        AND r.USER_ID = 'aEoLasF389EAlRn0eTkuBA';

//        select r.REVIEW_ID, r.DATEE, r.REVIEW_TEXT, b.BUSINESS_NAME, r.STARS, r.COOL_VOTES, r.FUNNY_VOTES, r.USEFUL_VOTES
//        from REVIEWS r, BUSINESS b
//        where r.BUSINESS_ID = b.BUSINESS_ID
//        AND r.USER_ID = '0vscrHoajVRa1Yk19XWdwA'
//        AND r.STARS > 0
//        AND r.TOTAL_VOTES > 0
//        AND r.DATEE >= '2007-02-21'
//        AND r.DATEE <= '2021-12-31'
//        ;

        StringBuilder reviewsQuery = new StringBuilder();
        StringBuilder reviewProperties = new StringBuilder();

        if (reviewsClass.getStar_value() != null)
            if (reviewsClass.getStar_value().length() > 0) {
                if (reviewProperties.length() > 0)
                    reviewProperties.append(" AND r.STARS ").append(reviewsClass.getStar_operator()).append(" ").append(reviewsClass.getStar_value());
                else
                    reviewProperties = new StringBuilder(" AND r.STARS " + reviewsClass.getStar_operator() + " " + reviewsClass.getStar_value());
            }
        if (reviewsClass.getVotes_value() != null)
            if (reviewsClass.getVotes_value().length() > 0) {
                if (reviewProperties.length() > 0)
                    reviewProperties.append(" AND r.TOTAL_VOTES ").append(reviewsClass.getVotes_operator()).append(" ").append(reviewsClass.getVotes_value());
                else
                    reviewProperties = new StringBuilder(" AND r.TOTAL_VOTES " + reviewsClass.getVotes_operator() + " " + reviewsClass.getVotes_value());
            }
        if (reviewsClass.getFrom_date() != null)
            if (reviewsClass.getFrom_date().length() == 10) {
                if (reviewProperties.length() > 0)
                    reviewProperties.append(" AND r.DATEE >= ").append("'").append(reviewsClass.getFrom_date()).append("'");
                else
                    reviewProperties = new StringBuilder(" AND r.DATEE >= " + "'" + reviewsClass.getFrom_date() + "'");
            }
        if (reviewsClass.getTo_date() != null)
            if (reviewsClass.getTo_date().length() == 10) {
                if (reviewProperties.length() > 0)
                    reviewProperties.append(" AND r.DATEE <= ").append("'").append(reviewsClass.getTo_date()).append("'");
                else
                    reviewProperties = new StringBuilder(" AND r.DATEE <= " + "'" + reviewsClass.getTo_date() + "'");
            }

        if (reviewProperties.length() > 0) {
            reviewsQuery = new StringBuilder("select r.REVIEW_ID, r.DATEE, r.REVIEW_TEXT, b.BUSINESS_NAME, r.STARS, r.COOL_VOTES, r.FUNNY_VOTES, r.USEFUL_VOTES" +
                    " from REVIEWS r, BUSINESS b" +
                    " where r.BUSINESS_ID = b.BUSINESS_ID AND r.USER_ID = '"+selectedUserID+"'" + reviewProperties+
                    ";");
        } else {
            reviewsQuery = new StringBuilder("select r.REVIEW_ID, r.DATEE, r.REVIEW_TEXT, b.BUSINESS_NAME, r.STARS, r.COOL_VOTES, r.FUNNY_VOTES, r.USEFUL_VOTES" +
                    " from REVIEWS r, BUSINESS b" +
                    " where r.BUSINESS_ID = b.BUSINESS_ID AND r.USER_ID = '"+selectedUserID+"';");
        }

        ResultSet resultSet = statement.executeQuery(reviewsQuery.toString());
        rtableData.clear();

        //r.REVIEW_ID, r.DATEE, r.REVIEW_TEXT, yu.USER_NAME,r.STARS, r.COOL_VOTES, r.FUNNY_VOTES, r.USEFUL_VOTES
        //r.REVIEW_ID, r.DATEE, r.REVIEW_TEXT, b.BUSINESS, r.STARS, r.COOL_VOTES, r.FUNNY_VOTES, r.USEFUL_VOTES
        while (resultSet.next()) {
            rtableData.add(new BreviewsTableEntry(resultSet.getString(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4),
                    resultSet.getString(5), resultSet.getString(6),
                    resultSet.getString(7), resultSet.getString(8)));
        }

        String[] columns = {"DATE", "TEXT", "BUSINESS NAME", "STARS", "COOL VOTES", "FUNNY VOTES", "USEFUL VOTES"};
        String[][] rowsData = new String[rtableData.size()][7];

        for (int i = 0; i < rtableData.size(); i++) {
            BreviewsTableEntry obj = rtableData.get(i);
            rowsData[i][0] = obj.datee;
            rowsData[i][1] = obj.reviewText;
            rowsData[i][2] = obj.userName;
            rowsData[i][3] = obj.stars;
            rowsData[i][4] = obj.coolVotes;
            rowsData[i][5] = obj.funnyVotes;
            rowsData[i][6] = obj.usefulVotes;

        }

        //------------[[Open new JFrame with table and populate it]]----------------//
        NewFrame nf = new NewFrame();
        nf.makeAndDisplayTable(columns, rowsData, selectedUserName);
    }

    public static void main(String[] args) throws SQLException {
        YelpUI yelpuiframe = new YelpUI();

        if (yelpuiframe.CONNECTION_ESTABLISHED)
        {
            yelpuiframe.initMainCategoryUI();
        }

    }

}
