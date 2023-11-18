package org.daviddang;

import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class WeatherAppGui extends JFrame {
    private JSONObject weatherData;

    public WeatherAppGui(){
        super("Weather App");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(450,650);

        setLocationRelativeTo(null);

        setLayout(null);

        setResizable(false);
        
        addGuiComponent();
    }

    private void addGuiComponent() {
        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(15,15,351,45);
        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(searchTextField);

        JLabel weatherConditionImage = new JLabel(loadImage("src/main/java/org/daviddang/assets/cloudy.png"));
        weatherConditionImage.setBounds(0,125,450,217);
        add(weatherConditionImage);

        JLabel temperatureText = new JLabel("10 C");
        temperatureText.setBounds(0,350,450,54);
        temperatureText.setFont(new Font("Dialog", Font.BOLD, 48));
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);

        JLabel weatherConditionDescription = new JLabel("Cloudy");
        weatherConditionDescription.setBounds(0,405,450,36);
        weatherConditionDescription.setFont(new Font("Dialog",Font.PLAIN,32));
        weatherConditionDescription.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDescription);

        JLabel humidityImage = new JLabel(loadImage("src/main/java/org/daviddang/assets/humidity.png"));
        humidityImage.setBounds(15,500,74,66);
        add(humidityImage);

        JLabel humidityText = new JLabel("<html><b>Humidity</b> 100%</html>");
        humidityText.setBounds(90,500,85,55);
        humidityText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(humidityText);

        JLabel windSpeedImage = new JLabel(loadImage("src/main/java/org/daviddang/assets/windspeed.png"));
        windSpeedImage.setBounds(220,500,74,66);
        add(windSpeedImage);

        JLabel windSpeedText = new JLabel("<html><b>Windspeed</b> 15km/h</html>");
        windSpeedText.setBounds(310,500,85,55);
        windSpeedText.setFont(new Font("Dialog",Font.PLAIN,16));
        add(windSpeedText);

        JButton searchButton = new JButton(loadImage("src/main/java/org/daviddang/assets/search.png"));
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375,13,47,45);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = searchTextField.getText();

                if (userInput.replaceAll("\\s", "").length() <= 0){
                    return;
                }

                weatherData = WeatherApp.getWeatherData(userInput);

                String weatherCondition = (String) weatherData.get("weather_condition");

                switch (weatherCondition){
                    case "Clear":
                        weatherConditionImage.setIcon(loadImage("src/main/java/org/daviddang/assets/clear.png"));
                        break;
                    case "Cloudy":
                        weatherConditionImage.setIcon(loadImage("src/main/java/org/daviddang/assets/cloudy.png"));
                        break;
                    case "Rain":
                        weatherConditionImage.setIcon(loadImage("src/main/java/org/daviddang/assets/rain.png"));
                        break;
                    case "Snow":
                        weatherConditionImage.setIcon(loadImage("src/main/java/org/daviddang/assets/snow.png"));
                        break;
                }

                double temperature = (double) weatherData.get("temperature");
                temperatureText.setText(temperature + " C");

                weatherConditionDescription.setText(weatherCondition);

                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");

                double windspeed = (double) weatherData.get("windspeed");
                windSpeedText.setText("<html><b>Windspeed</b> " + windspeed + "km/h</html>");


            }
        });
        add(searchButton);
    }

    private ImageIcon loadImage(String resourcePath){
        try {
            BufferedImage image = ImageIO.read(new File(resourcePath));
            return new ImageIcon(image);
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("Could not find resource");
        return null;
    }
}
