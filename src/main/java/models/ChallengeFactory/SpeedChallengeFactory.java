package models.ChallengeFactory;

import models.Challenge.Challenge;
import models.Challenge.SpeedChallenge;

public class SpeedChallengeFactory extends ChallengeFactory {
    @Override
    public Challenge createChallenge(int challengeId, String challengeName, String description, int score, int targetAmount) {
        return new SpeedChallenge(challengeId, challengeName, description, score, targetAmount);
    }
}
