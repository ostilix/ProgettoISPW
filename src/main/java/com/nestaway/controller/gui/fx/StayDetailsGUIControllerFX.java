package com.nestaway.controller.gui.fx;

import com.nestaway.bean.ReviewBean;
import com.nestaway.bean.StayBean;
import com.nestaway.controller.app.ReviewController;
import com.nestaway.exception.NotFoundException;
import com.nestaway.exception.OperationFailedException;
import com.nestaway.utils.SessionManager;
import com.nestaway.utils.view.fx.FilesFXML;
import com.nestaway.utils.view.fx.PageManagerSingleton;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.time.LocalDate;
import java.util.List;

public class StayDetailsGUIControllerFX extends AbstractGUIControllerFX {

    @FXML
    Pagination numberPage;

    @FXML
    Label title;

    @FXML
    Label city;

    @FXML
    Label address;

    @FXML
    Label guests;

    @FXML
    Label rooms;

    @FXML
    Label bathrooms;

    @FXML
    Label price;

    @FXML
    Label description;

    @FXML
    Label username;

    @FXML
    Button back;

    @FXML
    Button book;

    @FXML
    Button management;

    @FXML VBox reviewCard1, reviewCard2, reviewCard3;

    VBox[] reviewsCards;

    List<ReviewBean> reviews;

    StayBean stay;

    @FXML
    ImageView stayImage;


    @FXML
    public void goBack() {
        resetMsg(errorMsg);
        try {
            SessionManager.getSessionManager().getSessionFromId(currentSession).setStay(null);
            PageManagerSingleton.getInstance().goBack(currentSession);
        } catch (OperationFailedException | NotFoundException e) {
            setMsg(errorMsg, e.getMessage());
        }
    }

    @FXML
    public void bookStay() {
        StayBean stayBean = SessionManager.getSessionManager().getSessionFromId(currentSession).getStay();
        LocalDate checkIn = SessionManager.getSessionManager().getSessionFromId(currentSession).getCheckIn();
        LocalDate checkOut = SessionManager.getSessionManager().getSessionFromId(currentSession).getCheckOut();
        goNext(FilesFXML.BOOKING.getPath());
    }

    @FXML
    public void bookManagement() {
        setMsg(errorMsg, "Not implemented yet.");
    }

    public void initialize(Integer session) throws OperationFailedException {
        this.currentSession = session;
        resetMsg(errorMsg);

        stay = SessionManager.getSessionManager().getSessionFromId(currentSession).getStay();

        title.setText(stay.getName());
        city.setText(stay.getCity());
        address.setText(stay.getAddress());
        guests.setText(String.valueOf(stay.getMaxGuests()));
        rooms.setText(String.valueOf(stay.getNumRooms()));
        bathrooms.setText(String.valueOf(stay.getNumBathrooms()));
        price.setText(String.valueOf(stay.getPricePerNight()));
        description.setText(stay.getDescription());
        username.setText(stay.getHostUsername());

        stayImage.setImage(new Image(getClass().getResource("/images/stay6.png").toExternalForm()));
        applyRoundedCorners(stayImage, 30, 30);

        reviewsCards = new VBox[]{reviewCard1, reviewCard2, reviewCard3};
        int maxCards = 3;


        ReviewController reviewController = new ReviewController();
        reviews = reviewController.getReviewsByStay(stay.getIdStay());

        int numPages = (reviews.size() / maxCards);
        if(reviews.size() % maxCards != 0){
            numPages++;
        }
        numberPage.setPageCount(numPages);
        numberPage.setMaxPageIndicatorCount(Math.min(numPages/2, 10));
        numberPage.currentPageIndexProperty().addListener(((obs, oldIndex, newIndex) -> createPage(newIndex.intValue(), maxCards)));
        createPage(0, maxCards);
    }

    private void createPage(Integer pageIndex, Integer maxReviews){
        resetMsg(errorMsg);

        reviewCard1.setVisible(false);
        reviewCard2.setVisible(false);
        reviewCard3.setVisible(false);

        int max = Math.min(maxReviews, reviews.size() - pageIndex * maxReviews);

        for (int i = 0; i < max; i++) {
            reviewsCards[i].setVisible(true);
            ObservableList<Node> elements = reviewsCards[i].getChildren();
            ReviewBean review = reviews.get(pageIndex * maxReviews + i);

            Label ratingLabel = (Label) ((HBox) elements.get(0)).getChildren().get(0);
            Label commentLabel = (Label) ((HBox) elements.get(1)).getChildren().get(0);
            Label dateLabel = (Label) ((HBox) elements.get(2)).getChildren().get(0);

            ratingLabel.setText("Rating: " + review.getRating() + "/5");
            commentLabel.setText("Comment: " + review.getComment());
            dateLabel.setText("Date: " + review.getDate());
        }
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
