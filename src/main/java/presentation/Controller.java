package presentation;

/**
 * @author Andrei Rotaru
 *
 * This class initializes the Main GUI (home screen) buttons and sets up the
 * environment of the application.
 */

public class Controller {
    private HomeGUI homeGUI;
    private ClientGUI clientGUI;
    private ProductGUI productGUI;
    private OrderGUI orderGUI;

    public void start(){
        homeGUI = new HomeGUI();
        clientGUI = new ClientGUI(homeGUI);
        productGUI = new ProductGUI(homeGUI);
        orderGUI = new OrderGUI(homeGUI);

        homeGUI.setVisible(true);

        activateButtons();
    }

    public void activateButtons(){
        homeGUI.addClientButtonActionListener(e->{
           homeGUI.setVisible(false);
           clientGUI.setVisible(true);
        });

        homeGUI.addProductButtonActionListener(e->{
            homeGUI.setVisible(false);
            productGUI.setVisible(true);
        });

        homeGUI.addOrderButtonActionListener(e->{
            homeGUI.setVisible(false);
            orderGUI.setVisible(true);
        });
    }
}
