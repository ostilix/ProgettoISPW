package com.nestaway.controller.gui.fx;

import com.nestaway.bean.StayBean;
import com.nestaway.controller.app.BookStayController;
import com.nestaway.exception.NotFoundException;
import com.nestaway.exception.OperationFailedException;
import com.nestaway.utils.SessionManager;
import com.nestaway.utils.view.fx.FilesFXML;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class ListStaysGUIControllerFX extends  AbstractGUIControllerFX {

    @FXML
    Pagination numberPage;

    @FXML
    VBox stayCard1;

    @FXML
    VBox stayCard2;

    @FXML
    VBox stayCard3;

    @FXML
    VBox stayCard4;

    @FXML
    VBox stayCard5;

    @FXML
    VBox stayCard6;

    @FXML
    ImageView stayImage1;

    @FXML
    ImageView stayImage2;

    @FXML
    ImageView stayImage3;

    @FXML
    ImageView stayImage4;

    @FXML
    ImageView stayImage5;

    @FXML
    ImageView stayImage6;

    ImageView[] stayImages;

    VBox[] stayCards;

    List<StayBean> stays;

    @FXML
    public void selectStay(ActionEvent stay) {
        Button button = (Button) stay.getSource();
        String nameStay = button.getText();
        StayBean stayBean = searchStay(nameStay);
        SessionManager.getSessionManager().getSessionFromId(currentSession).setStay(stayBean);
        goNext(FilesFXML.STAY.getPath());
    }

    public void initialize(Integer session) throws OperationFailedException, NotFoundException {
        this.currentSession = session;
        stayCards = new VBox[]{stayCard1, stayCard2, stayCard3, stayCard4, stayCard5, stayCard6};
        stayImages = new ImageView[]{stayImage1, stayImage2, stayImage3, stayImage4, stayImage5, stayImage6};
        int maxCards = 6;

        BookStayController bookStayController = new BookStayController();
        stays = bookStayController.findStays(SessionManager.getSessionManager().getSessionFromId(session).getCity(), SessionManager.getSessionManager().getSessionFromId(session).getCheckIn(), SessionManager.getSessionManager().getSessionFromId(session).getCheckOut(), SessionManager.getSessionManager().getSessionFromId(session).getNumGuests());

        int numPages = (stays.size() / maxCards);
        if(stays.size() % maxCards != 0){
            numPages++;
        }
        numberPage.setPageCount(numPages);
        numberPage.setMaxPageIndicatorCount(Math.min(numPages, 10));
        numberPage.currentPageIndexProperty().addListener(((obs, oldIndex, newIndex) -> createPage(newIndex.intValue(), maxCards)));
        createPage(0, maxCards);
    }

    private void createPage(Integer pageIndex, Integer maxStays) {
        resetMsg(errorMsg);

        for (int i = 0; i < stayCards.length; i++) {
            stayCards[i].setVisible(false);
            stayImages[i].setImage(null);
        }

        int max = Math.min(maxStays, stays.size() - pageIndex * maxStays);

        for (int i = 0; i < max; i++) {
            StayBean stay = stays.get(pageIndex * maxStays + i);

            stayCards[i].setVisible(true);

            ObservableList<Node> elements = stayCards[i].getChildren();
            ((Button) elements.get(0)).setText(stay.getName());
            ((Label) elements.get(1)).setText("Price: " + stay.getPricePerNight() + "€/night");
            ((Label) elements.get(2)).setText("Host: " + stay.getHostUsername());

            stayImages[i].setImage(new Image(getClass().getResource("/images/stay" + (pageIndex * maxStays + i + 1) + ".png").toExternalForm()));
            applyRoundedCorners(stayImages[i], 25, 25);
        }
    }


    private StayBean searchStay(String stayName){
        for(StayBean stay : stays){
            if(stay.getName().equals(stayName)){
                return stay;
            }
        }
        return null;
    }

    private void applyRoundedCorners(ImageView imageView, double arcWidth, double arcHeight) {
        Image img = imageView.getImage();
        if (img == null) return;

        double width = imageView.getFitWidth();
        double height = imageView.getFitHeight();

        if (width <= 0 || height <= 0) {
            width = img.getWidth();
            height = img.getHeight();
        }

        Rectangle clip = new Rectangle(width, height);
        clip.setArcWidth(arcWidth);
        clip.setArcHeight(arcHeight);
        imageView.setClip(clip);

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage roundedImage = imageView.snapshot(parameters, null);

        imageView.setClip(null);
        imageView.setImage(roundedImage);
    }
}
