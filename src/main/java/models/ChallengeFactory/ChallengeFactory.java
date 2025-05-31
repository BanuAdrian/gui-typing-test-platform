package models.ChallengeFactory;

import models.Challenge.Challenge;

public abstract class ChallengeFactory {
    public abstract Challenge createChallenge(int challengeId, String challengeName, String description, int score, int targetAmount);
}
