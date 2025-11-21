// LAB2: Social Media Management System with Lambda Expressions and Stream API
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;
import java.util.function.*;
import java.util.stream.*;

// Functional Interfaces for Lambda Expressions
@FunctionalInterface
interface ArithmeticOperation {
    double operate(double a, double b);
}

@FunctionalInterface
interface StringOperation {
    String process(String str);
}

@FunctionalInterface
interface NumberCheck {
    boolean check(int number);
}

@FunctionalInterface
interface NoArgOperation {
    String execute();
}

@FunctionalInterface
interface OneArgOperation<T> {
    T process(T arg);
}

@FunctionalInterface
interface TwoArgOperation<T, U, R> {
    R operate(T arg1, U arg2);
}

// Social Media Post Class
class SocialMediaPost {
    private String username;
    private String content;
    private int likes;
    private int shares;
    private String category;
    private boolean isPublished;

    public SocialMediaPost(String username, String content, int likes, int shares, String category, boolean isPublished) {
        this.username = username;
        this.content = content;
        this.likes = likes;
        this.shares = shares;
        this.category = category;
        this.isPublished = isPublished;
    }

    // Getters
    public String getUsername() { return username; }
    public String getContent() { return content; }
    public int getLikes() { return likes; }
    public int getShares() { return shares; }
    public String getCategory() { return category; }
    public boolean isPublished() { return isPublished; }

    @Override
    public String toString() {
        return String.format("User: %s | Content: %s | Likes: %d | Shares: %d | Category: %s | Published: %s",
                username, content.length() > 20 ? content.substring(0, 20) + "..." : content, 
                likes, shares, category, isPublished);
    }
}

public class LAB2 extends JFrame {
    private List<SocialMediaPost> posts;
    private DefaultTableModel tableModel;
    private JTable postsTable;
    private JTextArea outputArea;
    
    // UI Components
    private JTextField num1Field, num2Field, stringField, numberField;
    private JComboBox<String> operationComboBox;

    public LAB2() {
        posts = new ArrayList<>();
        initializeSampleData();
        initializeUI();
        setTitle("LAB2 - SocialSphere: Social Media Management System");
    }

    private void initializeSampleData() {
        posts.addAll(Arrays.asList(
            new SocialMediaPost("john_doe", "Beautiful sunset at the beach! 🌅 #vacation", 150, 25, "Travel", true),
            new SocialMediaPost("sarah_smith", "Just finished my morning workout! 💪 #fitness", 89, 12, "Fitness", true),
            new SocialMediaPost("tech_guru", "New smartphone review: The camera is amazing! 📱", 234, 45, "Technology", true),
            new SocialMediaPost("food_lover", "Homemade pizza recipe coming soon! 🍕", 67, 8, "Food", false),
            new SocialMediaPost("travel_bug", "Exploring ancient ruins in Greece 🏛️", 312, 67, "Travel", true),
            new SocialMediaPost("fitness_expert", "5 exercises for better posture", 123, 23, "Fitness", true),
            new SocialMediaPost("code_master", "Java streams make data processing so easy!", 178, 34, "Technology", true),
            new SocialMediaPost("book_worm", "Just started reading a new novel 📚", 45, 5, "Entertainment", false),
            new SocialMediaPost("photo_artist", "Capturing nature's beauty through my lens 📸", 267, 89, "Photography", true),
            new SocialMediaPost("music_lover", "My new playlist for coding sessions 🎵", 78, 15, "Music", true)
        ));
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Create main panels
        JPanel inputPanel = createInputPanel();
        JPanel tablePanel = createTablePanel();
        JPanel outputPanel = createOutputPanel();

        // Add panels to frame
        add(inputPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(outputPanel, BorderLayout.SOUTH);

        pack();
        setSize(1200, 800);
        setLocationRelativeTo(null);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Part A: Lambda Expressions"));

        // Arithmetic Operations Panel
        JPanel arithmeticPanel = new JPanel(new FlowLayout());
        arithmeticPanel.add(new JLabel("Number 1:"));
        num1Field = new JTextField(5);
        arithmeticPanel.add(num1Field);
        
        arithmeticPanel.add(new JLabel("Number 2:"));
        num2Field = new JTextField(5);
        arithmeticPanel.add(num2Field);

        String[] operations = {"Addition", "Subtraction", "Multiplication", "Division"};
        operationComboBox = new JComboBox<>(operations);
        arithmeticPanel.add(operationComboBox);

        JButton calculateBtn = new JButton("Calculate");
        calculateBtn.addActionListener(this::performArithmetic);
        arithmeticPanel.add(calculateBtn);

        // String Operations Panel
        JPanel stringPanel = new JPanel(new FlowLayout());
        stringPanel.add(new JLabel("String:"));
        stringField = new JTextField(15);
        stringPanel.add(stringField);

        JButton reverseBtn = new JButton("Reverse");
        reverseBtn.addActionListener(e -> performStringOperation("reverse"));
        stringPanel.add(reverseBtn);

        JButton vowelBtn = new JButton("Count Vowels");
        vowelBtn.addActionListener(e -> performStringOperation("vowels"));
        stringPanel.add(vowelBtn);

        JButton upperBtn = new JButton("To Upper Case");
        upperBtn.addActionListener(e -> performStringOperation("uppercase"));
        stringPanel.add(upperBtn);

        // Number Check Panel
        JPanel numberPanel = new JPanel(new FlowLayout());
        numberPanel.add(new JLabel("Number:"));
        numberField = new JTextField(5);
        numberPanel.add(numberField);

        JButton evenBtn = new JButton("Check Even");
        evenBtn.addActionListener(e -> checkNumber("even"));
        numberPanel.add(evenBtn);

        JButton oddBtn = new JButton("Check Odd");
        oddBtn.addActionListener(e -> checkNumber("odd"));
        numberPanel.add(oddBtn);

        JButton primeBtn = new JButton("Check Prime");
        primeBtn.addActionListener(e -> checkNumber("prime"));
        numberPanel.add(primeBtn);

        panel.add(arithmeticPanel);
        panel.add(stringPanel);
        panel.add(numberPanel);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Social Media Posts Database"));

        // Table setup
        String[] columns = {"Username", "Content", "Likes", "Shares", "Category", "Published"};
        tableModel = new DefaultTableModel(columns, 0);
        postsTable = new JTable(tableModel);
        updateTable();

        JScrollPane scrollPane = new JScrollPane(postsTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Stream Operations Panel
        JPanel streamPanel = new JPanel(new GridLayout(2, 5, 5, 5));
        streamPanel.setBorder(BorderFactory.createTitledBorder("Part C: Stream API Operations"));
        
        String[] streamButtons = {
            "Filter Published", "Sort by Likes", "Map Usernames", 
            "Max Likes", "Count Posts", "Distinct Categories",
            "Limit 3", "Skip 2", "Any Match >200", "All Match >50"
        };

        for (String btnText : streamButtons) {
            JButton btn = new JButton(btnText);
            btn.addActionListener(this::performStreamOperation);
            streamPanel.add(btn);
        }

        // Additional operations panel
        JPanel additionalPanel = new JPanel(new FlowLayout());
        additionalPanel.setBorder(BorderFactory.createTitledBorder("Additional Operations"));
        
        JButton demoLambdasBtn = new JButton("Demo All Lambdas");
        demoLambdasBtn.addActionListener(e -> demonstrateVariousLambdas());
        additionalPanel.add(demoLambdasBtn);

        JButton clearOutputBtn = new JButton("Clear Output");
        clearOutputBtn.addActionListener(e -> outputArea.setText(""));
        additionalPanel.add(clearOutputBtn);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(streamPanel, BorderLayout.NORTH);
        southPanel.add(additionalPanel, BorderLayout.SOUTH);

        panel.add(southPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createOutputPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Output Console"));

        outputArea = new JTextArea(10, 80);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (SocialMediaPost post : posts) {
            tableModel.addRow(new Object[]{
                post.getUsername(),
                post.getContent(),
                post.getLikes(),
                post.getShares(),
                post.getCategory(),
                post.isPublished() ? "Yes" : "No"
            });
        }
    }

    // Part A: Lambda Expressions Implementation
    private void performArithmetic(ActionEvent e) {
        try {
            double num1 = Double.parseDouble(num1Field.getText());
            double num2 = Double.parseDouble(num2Field.getText());
            String operation = (String) operationComboBox.getSelectedItem();

            // Lambda expression for arithmetic operations
            ArithmeticOperation lambda;
            switch (operation) {
                case "Addition": 
                    lambda = (a, b) -> a + b; 
                    break;
                case "Subtraction": 
                    lambda = (a, b) -> a - b; 
                    break;
                case "Multiplication": 
                    lambda = (a, b) -> a * b; 
                    break;
                case "Division": 
                    lambda = (a, b) -> {
                        if (b == 0) throw new ArithmeticException("Division by zero!");
                        return a / b;
                    }; 
                    break;
                default: 
                    lambda = (a, b) -> 0;
            }

            double result = lambda.operate(num1, num2);
            outputArea.append(String.format("ARITHMETIC: %.2f %s %.2f = %.2f\n", 
                num1, 
                getOperationSymbol(operation), 
                num2, result));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers!");
        } catch (ArithmeticException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private String getOperationSymbol(String operation) {
        switch (operation) {
            case "Addition": return "+";
            case "Subtraction": return "-";
            case "Multiplication": return "×";
            case "Division": return "÷";
            default: return "?";
        }
    }

    private void performStringOperation(String operation) {
        String input = stringField.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a string!");
            return;
        }

        // Lambda expressions for string operations
        StringOperation lambda;
        switch (operation) {
            case "reverse":
                lambda = str -> new StringBuilder(str).reverse().toString();
                String reversed = lambda.process(input);
                outputArea.append("STRING REVERSAL: '" + input + "' -> '" + reversed + "'\n");
                break;
            case "vowels":
                lambda = str -> {
                    long count = str.toLowerCase().chars()
                            .filter(ch -> "aeiou".indexOf(ch) != -1)
                            .count();
                    return String.valueOf(count);
                };
                String vowelCount = lambda.process(input);
                outputArea.append("VOWEL COUNT: '" + input + "' has " + vowelCount + " vowels\n");
                break;
            case "uppercase":
                lambda = String::toUpperCase;
                String upper = lambda.process(input);
                outputArea.append("UPPERCASE: '" + input + "' -> '" + upper + "'\n");
                break;
        }
    }

    private void checkNumber(String checkType) {
        try {
            int number = Integer.parseInt(numberField.getText());
            
            // Lambda expressions for number checks
            NumberCheck lambda;
            String result;

            switch (checkType) {
                case "even":
                    lambda = n -> n % 2 == 0;
                    result = lambda.check(number) ? "EVEN" : "NOT EVEN";
                    outputArea.append("EVEN CHECK: " + number + " is " + result + "\n");
                    break;
                case "odd":
                    lambda = n -> n % 2 != 0;
                    result = lambda.check(number) ? "ODD" : "NOT ODD";
                    outputArea.append("ODD CHECK: " + number + " is " + result + "\n");
                    break;
                case "prime":
                    lambda = n -> {
                        if (n < 2) return false;
                        if (n == 2) return true;
                        if (n % 2 == 0) return false;
                        for (int i = 3; i <= Math.sqrt(n); i += 2) {
                            if (n % i == 0) return false;
                        }
                        return true;
                    };
                    result = lambda.check(number) ? "PRIME" : "NOT PRIME";
                    outputArea.append("PRIME CHECK: " + number + " is " + result + "\n");
                    break;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid integer!");
        }
    }

    // Part C: Stream API Operations
    private void performStreamOperation(ActionEvent e) {
        String command = ((JButton) e.getSource()).getText();
        outputArea.append("\n--- STREAM OPERATION: " + command + " ---\n");

        switch (command) {
            case "Filter Published":
                // Using filter() with lambda
                List<SocialMediaPost> publishedPosts = posts.stream()
                    .filter(post -> post.isPublished())
                    .collect(Collectors.toList());
                publishedPosts.forEach(post -> outputArea.append("✓ " + post + "\n"));
                outputArea.append("Total published posts: " + publishedPosts.size() + "\n");
                break;

            case "Sort by Likes":
                // Using sorted() with comparator lambda
                posts.stream()
                    .sorted((p1, p2) -> Integer.compare(p2.getLikes(), p1.getLikes()))
                    .forEach(post -> outputArea.append("❤️ " + post.getLikes() + " likes - " + post + "\n"));
                break;

            case "Map Usernames":
                // Using map() to transform objects
                List<String> usernames = posts.stream()
                    .map(SocialMediaPost::getUsername)
                    .collect(Collectors.toList());
                usernames.forEach(username -> outputArea.append("👤 " + username + "\n"));
                break;

            case "Max Likes":
                // Using max() with comparator
                posts.stream()
                    .max(Comparator.comparingInt(SocialMediaPost::getLikes))
                    .ifPresent(post -> 
                        outputArea.append("🏆 MAX LIKES: " + post.getLikes() + " - " + post + "\n"));
                break;

            case "Count Posts":
                // Using count()
                long totalPosts = posts.stream().count();
                long publishedCount = posts.stream().filter(SocialMediaPost::isPublished).count();
                outputArea.append("📊 TOTAL POSTS: " + totalPosts + " | PUBLISHED: " + publishedCount + "\n");
                break;

            case "Distinct Categories":
                // Using distinct()
                List<String> categories = posts.stream()
                    .map(SocialMediaPost::getCategory)
                    .distinct()
                    .collect(Collectors.toList());
                outputArea.append("📁 DISTINCT CATEGORIES: " + String.join(", ", categories) + "\n");
                break;

            case "Limit 3":
                // Using limit()
                posts.stream()
                    .limit(3)
                    .forEach(post -> outputArea.append("⏹️ " + post + "\n"));
                break;

            case "Skip 2":
                // Using skip()
                posts.stream()
                    .skip(2)
                    .forEach(post -> outputArea.append("⏭️ " + post + "\n"));
                break;

            case "Any Match >200":
                // Using anyMatch()
                boolean hasViral = posts.stream()
                    .anyMatch(post -> post.getLikes() > 200);
                outputArea.append("🔥 ANY VIRAL POSTS (>200 likes): " + hasViral + "\n");
                break;

            case "All Match >50":
                // Using allMatch()
                boolean allPopular = posts.stream()
                    .allMatch(post -> post.getLikes() > 50);
                outputArea.append("⭐ ALL POPULAR (>50 likes): " + allPopular + "\n");
                break;
        }
    }

    // Part B: Various Lambda Types Demonstration
    private void demonstrateVariousLambdas() {
        outputArea.append("\n" + "=".repeat(80) + "\n");
        outputArea.append("PART B: VARIOUS LAMBDA EXPRESSION TYPES DEMONSTRATION\n");
        outputArea.append("=".repeat(80) + "\n");

        // 1. Lambda with no arguments
        outputArea.append("\n1. LAMBDA WITH NO ARGUMENTS:\n");
        NoArgOperation welcomeMessage = () -> "🚀 Welcome to SocialSphere Management System!";
        NoArgOperation currentTime = () -> "⏰ Current time: " + new java.util.Date();
        outputArea.append("   " + welcomeMessage.execute() + "\n");
        outputArea.append("   " + currentTime.execute() + "\n");

        // 2. Lambda with one argument
        outputArea.append("\n2. LAMBDA WITH ONE ARGUMENT:\n");
        OneArgOperation<String> capitalize = str -> str.substring(0, 1).toUpperCase() + str.substring(1);
        OneArgOperation<String> addHashtag = str -> "#" + str.replace(" ", "");
        OneArgOperation<Integer> square = n -> n * n;
        
        outputArea.append("   Capitalize: 'social media' -> '" + capitalize.process("social media") + "'\n");
        outputArea.append("   Add Hashtag: 'java programming' -> '" + addHashtag.process("java programming") + "'\n");
        outputArea.append("   Square: 12 -> " + square.process(12) + "\n");

        // 3. Lambda with two arguments
        outputArea.append("\n3. LAMBDA WITH TWO ARGUMENTS:\n");
        TwoArgOperation<String, String, String> createPost = (user, content) -> 
            "📝 New post by @" + user + ": \"" + content + "\"";
        TwoArgOperation<Integer, Integer, Double> engagementScore = (likes, shares) -> 
            (likes * 1.0 + shares * 2.0) / 100.0;
        
        outputArea.append("   " + createPost.operate("admin", "System maintenance scheduled") + "\n");
        outputArea.append("   Engagement Score (150 likes, 25 shares): " + 
                         engagementScore.operate(150, 25) + "\n");

        // 4. Block Lambda Expression
        outputArea.append("\n4. BLOCK LAMBDA EXPRESSIONS:\n");
        TwoArgOperation<String, Integer, String> postAnalysis = (username, likes) -> {
            String performance;
            if (likes > 200) performance = "EXCELLENT";
            else if (likes > 100) performance = "GOOD";
            else if (likes > 50) performance = "AVERAGE";
            else performance = "NEEDS IMPROVEMENT";
            
            return String.format("📊 Analysis for @%s: %d likes → %s performance", 
                               username, likes, performance);
        };
        
        TwoArgOperation<List<SocialMediaPost>, String, Long> categoryStats = (postList, category) -> {
            long count = postList.stream()
                .filter(post -> post.getCategory().equalsIgnoreCase(category))
                .count();
            long totalLikes = postList.stream()
                .filter(post -> post.getCategory().equalsIgnoreCase(category))
                .mapToInt(SocialMediaPost::getLikes)
                .sum();
            return count > 0 ? totalLikes / count : 0;
        };
        
        outputArea.append("   " + postAnalysis.operate("john_doe", 150) + "\n");
        outputArea.append("   Average likes in 'Technology' category: " + 
                         categoryStats.operate(posts, "Technology") + "\n");

        // Additional Stream operations with collect and reduce
        outputArea.append("\n5. ADDITIONAL STREAM OPERATIONS:\n");
        
        // Using collect with grouping
        Map<String, Long> postsByCategory = posts.stream()
            .collect(Collectors.groupingBy(
                SocialMediaPost::getCategory, 
                Collectors.counting()
            ));
        outputArea.append("   Posts by category: " + postsByCategory + "\n");

        // Using reduce for total engagement
        int totalEngagement = posts.stream()
            .map(post -> post.getLikes() + post.getShares())
            .reduce(0, Integer::sum);
        outputArea.append("   Total engagement (likes + shares): " + totalEngagement + "\n");

        // Using forEach with method reference
        outputArea.append("   First 3 usernames: ");
        posts.stream()
            .limit(3)
            .map(SocialMediaPost::getUsername)
            .forEach(username -> outputArea.append("@" + username + " "));
        outputArea.append("\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
} catch (UnsupportedLookAndFeelException | ClassNotFoundException |
         InstantiationException | IllegalAccessException e) {

    try {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (Exception ex) {
        System.out.println("Look and feel setting failed, using default.");
    }
}

            
            LAB2 lab2 = new LAB2();
            lab2.setVisible(true);
            
            // Display welcome message
            SwingUtilities.invokeLater(() -> {
                lab2.outputArea.append("=".repeat(80) + "\n");
                lab2.outputArea.append("LAB2: Social Media Management System with Lambda Expressions & Stream API\n");
                lab2.outputArea.append("=".repeat(80) + "\n");
                lab2.outputArea.append("System initialized with " + lab2.posts.size() + " sample posts.\n");
                lab2.outputArea.append("Use the buttons above to demonstrate various lambda expressions and stream operations.\n\n");
            });
        });
    }
}