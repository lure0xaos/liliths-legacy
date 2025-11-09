package com.lilithsthrone.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @since 0.1.0
 * @version 0.1.0
 * @author Innoxia
 */
public class LoadingController implements Initializable {

	@FXML
	private Label labelWorldNumber;
	@FXML
	private ProgressBar progressBarLoading;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
}
