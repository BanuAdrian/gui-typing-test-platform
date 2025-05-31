package models.enums;

public enum ActionType {
    LOGIN, // X
    LOAD_USER_DATA, // X
    REGISTER_NEW_USER, // X
    START_TYPING_TEST, // X TypingTestController/TypingSession
    CALCULATE_WPM, // X TypingTestController/TypingSession
    CHOOSE_TEXT_CATEGORY, // X MainMenuController
    START_RANDOM_CHALLENGE, // X TypingTestController
    VERIFY_CHALLENGE_CRITERIA, // X TypingTestController
    VIEW_WPM_LEADERBOARD, // X MainMenuController
    UPDATE_WPM_LEADERBOARD, // X MainService
    SEND_NOTIFICATION, // X MainService
    CHECK_ACHIEVEMENTS, // X MainService
    VIEW_SCORE_LEADERBOARD, // X MainMenuController
    UPDATE_SCORE_LEADERBOARD, // X MainService
    VIEW_COMPLETED_CHALLENGES, // X MainMenuController
    VIEW_ACHIEVEMENTS, // X MainMenuController
    VIEW_NOTIFICATIONS, // X MainMenuController
    DELETE_NOTIFICATION, // X MainService
    LOGOUT // X MainMenuController
}
