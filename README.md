# [JavaFX GUI] Typing Test Platform

# Models List

1. User - represents a registered user
2. TypingTest - the data of a completed typing test for maintaining a history
3. TypingSession - a session of typing (for a test or a challenge)
4. Text - the content used for typing tests
5. Achievement – defines an achievement that can be unlocked by the user
6. TestsTakenAchievement – achievement granted when a certain number of tests is completed
7. SpeedAchievement – achievement granted when a certain typing speed is reached
8. Notification – message received by a user when they are surpassed in the WPM leaderboard
9. TestTimer – a timer that measures the duration of a typing test
10. Challenge – defines a challenge that can be taken by a user
11. SpeedChallenge – speed-based challenge
12. TimeChallenge – time-based challenge
13. LeaderboardEntry – an entry in the leaderboard

# Actions List

1. When the application starts, users can log in using their username
2. If a user with the entered name exists in the database, their data is loaded
3. If a user with the entered name does not exist in the database, a new entry is created
4. Users can take a test to check their typing speed
5. The system automatically calculates and displays the typing speed at the end of the test
6. Users can choose a category for the text they want to type
7. Users can opt to take a random challenge (e.g., type a text in under X seconds or at a speed of at least Y WPM) and earn a certain score
8. At the end of a challenge, the system checks whether the user's performance meets the challenge criteria
9. Users can view the leaderboard based on typing speed
10. The typing speed leaderboard is updated after each test
11. For every user surpassed in the WPM leaderboard by the current user, a notification is sent
12. At the end of a test, the system checks whether the user has unlocked any predefined achievements
13. Users can view the leaderboard based on challenge scores
14. The challenge score leaderboard is updated after each challenge
15. Users can view the list of completed challenges
16. Users can view the list of unlocked achievements
17. Users can view the list of system-generated notifications when they are surpassed in the typing speed leaderboard
18. Users can delete their own notifications
19. Users can log out from the system

