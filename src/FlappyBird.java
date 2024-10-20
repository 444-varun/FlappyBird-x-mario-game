import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class FlappyBird extends JFrame implements ActionListener, KeyListener {
    // main frame variables
    CardLayout cardLayout;
    JPanel mainPanel;
    GamePanel gamePanel;
    MenuPanel menuPanel;
    CharacterSelectionPanel characterSelectionPanel; // new character panel
    Image backImage;
    Image logo;
    Image bird1,bird2,bird3,bird4,bird5,bird6,bird7;
    

    public FlappyBird() {
        setTitle("Flappy Bird");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(360, 640);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Menu panel, character panel, and game panel
        menuPanel = new MenuPanel();
        gamePanel = new GamePanel();
        characterSelectionPanel = new CharacterSelectionPanel();

        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(gamePanel, "Game");
        mainPanel.add(characterSelectionPanel, "CharacterSelector");

        add(mainPanel);
        cardLayout.show(mainPanel, "Menu"); // show Menu on start

        setVisible(true);
    }

    // method to switch to the game
    public void startGame() {
        cardLayout.show(mainPanel, "Game");
        gamePanel.startGame(); // start game
    }

    // method to switch to the character panel
    public void showCharacterSelector() {
        cardLayout.show(mainPanel, "CharacterSelector");
    }

    // Inner class for the Home screen (Menu)
    class MenuPanel extends JPanel {
        public MenuPanel() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10); // set
            gbc.gridx = 0;
            gbc.gridy = 0;

            // start menu back img
            backImage = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();

           // game logo
           logo = new ImageIcon(getClass().getResource("./flappy x mario logo 20.png")).getImage();


           //birds images
           bird1 = new ImageIcon(getClass().getResource("./flappy bird yellow img 10.png")).getImage();
           bird2 = new ImageIcon(getClass().getResource("./flappy bird mario img 10.png")).getImage();
           bird3 = new ImageIcon(getClass().getResource("./flappy bird pink img 30.png")).getImage();
           bird4 = new ImageIcon(getClass().getResource("./flappy bird white img 40.png")).getImage();
           bird5 = new ImageIcon(getClass().getResource("./flappy bird beige img 50.png")).getImage();
           bird6 = new ImageIcon(getClass().getResource("./flappy bird green img 60.png")).getImage();
           bird7 = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
            // start image as a button
            ImageIcon startIcon = new ImageIcon(getClass().getResource("./final start button.png"));
            JButton startButton = new JButton(startIcon);
            startButton.setContentAreaFilled(false);
            startButton.setFocusPainted(false);
            startButton.addActionListener(e -> startGame());
            gbc.gridy = 1;
            add(startButton, gbc);

            // exit image as a button
            ImageIcon exitIcon = new ImageIcon(getClass().getResource("./final exit button.png"));
            JButton exitButton = new JButton(exitIcon);
            exitButton.setContentAreaFilled(false);
            exitButton.setBorderPainted(false);
            exitButton.setFocusPainted(false);
            exitButton.addActionListener(e -> System.exit(0));
            gbc.gridy = 2;
            add(exitButton, gbc);

            // character selector button
            ImageIcon characterSelectIcon = new ImageIcon(getClass().getResource("./final select character img 3.png"));
            JButton characterSelectorButton = new JButton(characterSelectIcon);
            characterSelectorButton.setContentAreaFilled(false);
            characterSelectorButton.setBorderPainted(false);
            characterSelectorButton.setFocusPainted(false);
            characterSelectorButton.addActionListener(e -> showCharacterSelector());
            gbc.gridy = 3;
            add(characterSelectorButton, gbc);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // start menu back Img
            g.drawImage(backImage, 0, 0, getWidth(), getHeight(), this);
            g.drawImage(logo,25,20, 300,100, this);
            g.drawImage(bird1, 255, 20, 50, 50, this);//yellow big bird
            g.drawImage(bird2, 30,477, 70, 70, this);//mario bird
            g.drawImage(bird3, 160, 110, 50, 50, this);//pink bird
            g.drawImage(bird4, 160, 420, 50, 50, this);//white bird
            g.drawImage(bird5, 30, 170, 50, 50, this);//brown bird
            g.drawImage(bird6, 290, 300, 50, 50, this);//green bird
            g.drawImage(bird7, 240, 490, 43, 43, this);// og bird
            
        }
    }

    class CharacterSelectionPanel extends JPanel {
        public CharacterSelectionPanel() {
            setLayout(new BorderLayout());
            setBackground(Color.BLACK);

            // create a panel for the character buttons using GridLayout
            JPanel characterGridPanel = new JPanel(new GridLayout(3, 3, 10, 10));
            characterGridPanel.setOpaque(false); // make background transparent

            String[] characterImages = {
                "./flappybird.png",
                "./flappy bird yellow img 10.png",
                "./flappy bird blue img 20.png",
                "./flappy bird pink img 30.png",
                "./flappy bird white img 40.png",
                "./flappy bird beige img 50.png",
                "./flappy bird green img 60.png",
                "/flappy bird mario img 10.png"
            };

            for (int i = 0; i < characterImages.length; i++) {
                ImageIcon characterIcon = new ImageIcon(getClass().getResource(characterImages[i]));
                JButton characterButton = new JButton(characterIcon);
                characterButton.setBorder(BorderFactory.createLineBorder(Color.white, 2));
                characterButton.setContentAreaFilled(false);
                characterButton.setFocusPainted(false);
                int characterIndex = i;
                characterButton.addActionListener(e -> selectCharacter(characterIndex));
                characterGridPanel.add(characterButton);
            }

            JPanel characterGridContainer = new JPanel(new BorderLayout());
            characterGridContainer.setOpaque(false);
            characterGridContainer.add(characterGridPanel, BorderLayout.CENTER);
            characterGridContainer.setPreferredSize(new Dimension(getWidth(), (int) (getHeight() * 0.7)));
            add(characterGridContainer, BorderLayout.CENTER);

            JPanel menuButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            menuButtonPanel.setOpaque(false);

            // Main Menu button
            ImageIcon menuIcon = new ImageIcon(getClass().getResource("./final main menu button.png"));
            JButton backButton = new JButton(menuIcon);
            backButton.setContentAreaFilled(false);
            backButton.setBorderPainted(false);
            backButton.setFocusPainted(false);
            backButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));
            menuButtonPanel.add(backButton);

            add(menuButtonPanel, BorderLayout.SOUTH);
        }

        public void selectCharacter(int characterIndex) {
            System.out.println("Character " + characterIndex + " Selected");
            gamePanel.setSelectedCharacter(characterIndex); // Update the selected character in GamePanel
            cardLayout.show(mainPanel, "Menu"); // Go back to Menu
        }
    }

    // Inner class for the game screen
    public class GamePanel extends JPanel implements ActionListener, KeyListener {
        int boardwidth = 360;
        int boardheight = 640;

        // images
        Image backgroundImage;
        Image birdImage;
        Image toppipeImage;
        Image bottompipeImage;
        Image gameOverImage;
        ImageIcon restartButtIcon;
        JButton restartbutton;

        // bird
        int birdX = boardwidth / 8;
        int birdY = boardheight / 2;
        int birdwidth = 34;
        int birdheight = 24;

        class Bird {
            int x = birdX;
            int y = birdY;
            int height = birdheight;
            int width = birdwidth;
            Image image;

            Bird(Image image) {
                this.image = image;
            }
        }

        // pipes
        int pipeX = boardwidth;
        int pipeY = 0;
        int pipewidth = 64;
        int pipeheight = 512;

        class Pipe {
            int x = pipeX;
            int y = pipeY;
            int width = pipewidth;
            int height = pipeheight;
            Image image;
            boolean passed = false;

            Pipe(Image image) {
                this.image = image;
            }
        }

        // game logic
        Bird bird;
        int velocityX = -4;
        int velocityY = 0;
        int gravity = 1;

        ArrayList<Pipe> pipes;
        Random random = new Random();

        Timer gameloop;
        Timer placePipesTimer;
        boolean gameOver = false;
        boolean isPaused = false; // to pause the game
        double score = 0;

        // audio clip
        Clip backgroundClip;
        Clip gameOverClip;

        // Add this to keep track of the selected character
        private int selectedCharacter = 0;
        String[] characterImages = {
            "./flappybird.png",
            "./flappy bird yellow img 10.png",
            "./flappy bird blue img 20.png",
            "./flappy bird pink img 30.png",
            "./flappy bird white img 40.png",
            "./flappy bird beige img 50.png",
            "./flappy bird green img 60.png",
            "/flappy bird mario img 10.png"
        };

        GamePanel() {
            setPreferredSize(new Dimension(boardwidth, boardheight));

            setFocusable(true);
            addKeyListener(this);

            // load images
            backgroundImage = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
            birdImage = new ImageIcon(getClass().getResource(characterImages[selectedCharacter])).getImage();
            toppipeImage = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
            bottompipeImage = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
            gameOverImage = new ImageIcon(getClass().getResource("./final Game over logo.png")).getImage();

            restartButtIcon = new ImageIcon(getClass().getResource("./final restart img.png"));
            // restart button
            restartbutton = new JButton(restartButtIcon);
            restartbutton.setPreferredSize(new Dimension(restartButtIcon.getIconWidth(), restartButtIcon.getIconHeight()));
            restartbutton.setContentAreaFilled(false); // make the button transparent
            restartbutton.setBorderPainted(false); // remove the border of the image
            restartbutton.setVisible(false); // initially hidden until game over
            restartbutton.addActionListener(e -> startGame()); // add action listener
            add(restartbutton);

            // main menu button on game over panel
            ImageIcon menuIcon = new ImageIcon(getClass().getResource("./final main menu button.png"));
            JButton menuButton = new JButton(menuIcon);
            menuButton.setContentAreaFilled(false);
            menuButton.setBorderPainted(false);
            menuButton.setFocusPainted(false);
            menuButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));
            add(menuButton);


            pipes = new ArrayList<>();
            gameloop = new Timer(1000 / 60, this); // 60 FPS
            placePipesTimer = new Timer(1500, e -> addPipe()); // add pipe every 2 seconds

            loadMusic();
        }
  
        public void loadMusic(){
            try{
                AudioInputStream bgStream = AudioSystem.getAudioInputStream(new File("C:\\Users\\varun\\OneDrive\\Desktop\\FlappyBird\\src\\NewMarioTheme.wav"));
                backgroundClip = AudioSystem.getClip();
                backgroundClip.open(bgStream);


                AudioInputStream gameOverStream = AudioSystem.getAudioInputStream(new File ("src/NewMarioDeath.wav"));
                gameOverClip = AudioSystem.getClip();
                gameOverClip.open(gameOverStream);
            }catch(UnsupportedAudioFileException | IOException | LineUnavailableException e){
                     e.printStackTrace();
            }
        }
        public void playBackgroundMusic(){
            if(backgroundClip != null && !backgroundClip.isRunning()){
                backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
                backgroundClip.start();
            }
        }

        public void stopBackgroundMusic(){
            if(backgroundClip != null && backgroundClip.isRunning()){
                backgroundClip.stop();
            }
        }
        public void stopGameOverMusic(){
            if(backgroundClip != null && !backgroundClip.isRunning()){
                backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
                gameOverClip.stop();
            }
        }

        public void playGameOverMusic(){
            if(gameOverClip != null){
                gameOverClip.start();
            }
        }
       
        // method to set the selected character
        public void setSelectedCharacter(int selectedCharacter) {
            this.selectedCharacter = selectedCharacter;
            // Initialize bird with the selected character image
            bird = new Bird(new ImageIcon(getClass().getResource(characterImages[selectedCharacter])).getImage());
        }

        public void startGame() {
            bird = new Bird(new ImageIcon(getClass().getResource(characterImages[selectedCharacter])).getImage());
            bird.y = boardheight / 2; // Reset bird position
            velocityY = 0; // Reset velocity
            pipes.clear(); // Clear pipes
            score = 0; // Reset score
            gameOver = false; // Reset game over state
            isPaused = false; // Reset pause state
            restartbutton.setVisible(false); // Hide restart button
            playBackgroundMusic();
            // Start the game loop and pipe generation
            gameloop.start();
            placePipesTimer.start();
           
            requestFocusInWindow(); // Focus on the game panel
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, null);
            // Draw bird
            g.drawImage(bird.image, bird.x, bird.y, bird.width, bird.height, null);

            // Draw pipes
            for (Pipe pipe : pipes) {
                g.drawImage(pipe.image, pipe.x, pipe.y, pipe.width, pipe.height, null);
            }

            // Draw score
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("Score: " + (int) score, 10, 30);

            // Draw game over screen
            if (gameOver) {
                g.drawImage(gameOverImage,(boardwidth-270)/2,(boardheight-100)/2,250,55,null);
                stopBackgroundMusic();
                playGameOverMusic();
                restartbutton.setVisible(true); // Show restart button
                
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!gameOver) {
                move(); // Update game state
                repaint(); // Redraw the game
            }
            // Handle game over conditions
            if (gameOver) {
                placePipesTimer.stop();
                gameloop.stop();
                playGameOverMusic();
                stopBackgroundMusic();
               

            }
        }

        public void move() {
            if (isPaused || gameOver) return; // Skip movement if paused or game over

            // Update bird's position with gravity
            velocityY += gravity; // Apply gravity
            bird.y += velocityY; // Update bird's position
            bird.y = Math.max(bird.y, 0); // Prevent bird from falling below the screen

            // Update pipes and check for collisions
            for (int i = 0; i < pipes.size(); i++) {
                Pipe pipe = pipes.get(i);
                pipe.x += velocityX; // Move pipe

                // Check for score increment and collisions
                if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                    pipe.passed = true;
                    score += 0.5; // Score increment logic
                }

                if (collision(bird, pipe)) {
                    gameOver = true; // Set game over if collision detected
                }
            }

            // Check if the bird falls below the screen
            if (bird.y > boardheight) {
                gameOver = true;
            }

            // Remove pipes that are off-screen
            pipes.removeIf(pipe -> pipe.x + pipe.width < 0);
        }

        // Function to add new pipes
        public void addPipe() {
            int gapHeight = 150; // Gap height between pipes
            int pipeHeight = random.nextInt(boardheight - gapHeight - 100) + 50; // Random pipe height
            Pipe topPipe = new Pipe(toppipeImage);
            Pipe bottomPipe = new Pipe(bottompipeImage);
            topPipe.y = pipeHeight - topPipe.height;
            bottomPipe.y = pipeHeight + gapHeight; // Position bottom pipe
            pipes.add(topPipe);
            pipes.add(bottomPipe);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                // Jump functionality
                if (!gameOver) {
                 
                    velocityY = -10; // Jump
                } else {
                    // Restart game if it's over
                    startGame();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}

        @Override
        public void keyTyped(KeyEvent e) {}

        // Collision detection logic
        public boolean collision(Bird bird, Pipe pipe) {
            Rectangle birdRect = new Rectangle(bird.x, bird.y, bird.width, bird.height);
            Rectangle pipeRect = new Rectangle(pipe.x, pipe.y, pipe.width, pipe.height);
            return birdRect.intersects(pipeRect);
        }
    }

    public static void main(String[] args) {
        new FlappyBird();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {}
}