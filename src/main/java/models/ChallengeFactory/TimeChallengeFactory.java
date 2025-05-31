package models.ChallengeFactory;

import models.Challenge.Challenge;
import models.Challenge.TimeChallenge;

public class TimeChallengeFactory extends ChallengeFactory {
    @Override
    public Challenge createChallenge(int challengeId, String challengeName, String description, int score, int targetAmount) {
        return new TimeChallenge(challengeId, challengeName, description, score, targetAmount);
    }
}
