package ho.core.training;

import ho.core.constants.TrainingType;

public class TrainingPlayer {
	private String pName = "";
	private int pMinutesPlayedAsGK = 0;
	private int pMinutesPlayedAsWB = 0;
	private int pMinutesPlayedAsCD = 0;
	private int pMinutesPlayedAsW = 0;
	private int pMinutesPlayedAsIM = 0;
	private int pMinutesPlayedAsFW = 0;
	private int pMinutesPlayedAsSP = 0;
	private int pTotalMinutesPlayed = 0;
	private int pMinutesForTraining = 0;
	private double pTrainingPoints = 0;

	public TrainingPlayer()
	{
	}
	public void setMinutesPlayedAsGK(int minutes)
	{
		pMinutesPlayedAsGK = minutes;
		pTotalMinutesPlayed += minutes;
	}
	public int getMinutesPlayedAsGK()
	{
		return pMinutesPlayedAsGK;
	}
	public void setMinutesPlayedAsWB(int minutes)
	{
		pMinutesPlayedAsWB = minutes;
		pTotalMinutesPlayed += minutes;
	}
	public int getMinutesPlayedAsWB()
	{
		return pMinutesPlayedAsWB;
	}
	public void setMinutesPlayedAsCD(int minutes)
	{
		pMinutesPlayedAsCD = minutes;
		pTotalMinutesPlayed += minutes;
	}
	public int getMinutesPlayedAsCD()
	{
		return pMinutesPlayedAsCD;
	}
	public void setMinutesPlayedAsW(int minutes)
	{
		pMinutesPlayedAsW = minutes;
		pTotalMinutesPlayed += minutes;
	}
	public int getMinutesPlayedAsW()
	{
		return pMinutesPlayedAsW;
	}
	public void setMinutesPlayedAsIM(int minutes)
	{
		pMinutesPlayedAsIM = minutes;
		pTotalMinutesPlayed += minutes;
	}
	public int getMinutesPlayedAsIM()
	{
		return pMinutesPlayedAsIM;
	}
	public void setMinutesPlayedAsFW(int minutes)
	{
		pMinutesPlayedAsFW = minutes;
		pTotalMinutesPlayed += minutes;
	}
	public int getMinutesPlayedAsFW()
	{
		return pMinutesPlayedAsFW;
	}
	public void setMinutesPlayedAsSP(int minutes)
	{
		pMinutesPlayedAsSP = minutes;
		pTotalMinutesPlayed += minutes;
	}
	public int getMinutesPlayedAsSP()
	{
		return pMinutesPlayedAsSP;
	}
	public int getMinutesPlayed()
	{
		return pTotalMinutesPlayed;
	}
	public String Name()
	{
		return pName;
	}
	public void Name(String sName)
	{
		pName = sName;
	}
	public boolean PlayerHasPlayed()
	{
		return pTotalMinutesPlayed > 0;
	}
	public void setMinutesForTraining(int minutes)
	{
		pMinutesForTraining = minutes;
	}
	public int getMinutesForTraining()
	{
		return pMinutesForTraining;
	}
	public void setTrainingPoints(double points)
	{
		pTrainingPoints = points;
	}
	public double getTrainingPoints()
	{
		return pTrainingPoints;
	}
	public void CalculateTrainingPoints(TrainingPoint trPoint, int trainingType)
	{
		int tmp = 0;
		switch (trainingType)
		{
			case TrainingType.GOALKEEPING:
				pMinutesForTraining = pMinutesPlayedAsGK;
				if (pMinutesForTraining > 0) {
					if (pMinutesForTraining > 90) {
						pMinutesForTraining = 90;
					}
					pTrainingPoints = ((trPoint.getTrainingPoint(trainingType, 
							new Integer(TrainingPosition.keeper))) * (pMinutesForTraining / 90d));
				}
				break;
			case TrainingType.DEFENDING:
				pMinutesForTraining = pMinutesPlayedAsCD;
				if (pMinutesForTraining > 0) {
					if (pMinutesForTraining > 90) {
						pMinutesForTraining = 90;
					}
					pTrainingPoints = ((trPoint.getTrainingPoint(trainingType, 
							new Integer(TrainingPosition.centralDefender))) * (pMinutesForTraining / 90d));
				}
				if (pMinutesForTraining < 90)
				{
					tmp = pMinutesPlayedAsWB;
					if (tmp > 0) {
						if (pMinutesForTraining + tmp > 90) {
							tmp = 90 - pMinutesForTraining;
						}
						pMinutesForTraining += tmp;
						pTrainingPoints += ((trPoint.getTrainingPoint(trainingType, 
								new Integer(TrainingPosition.wingBack))) * (tmp / 90d));
					}
					if (pMinutesForTraining < 90)
					{
						tmp = pMinutesPlayedAsGK + pMinutesPlayedAsW + pMinutesPlayedAsIM + pMinutesPlayedAsFW;
						if (tmp > 0) {
							if (pMinutesForTraining + tmp > 90) {
								tmp = 90 - pMinutesForTraining;
							}
							pMinutesForTraining += tmp;
							pTrainingPoints += ((trPoint.getTrainingPoint(trainingType, 
									new Integer(TrainingPosition.osmosis))) * (tmp / 90d));
						}
					}
				}
				break;
			case TrainingType.DEF_POSITIONS:
				pMinutesForTraining = pMinutesPlayedAsGK;
				if (pMinutesForTraining > 0) {
					if (pMinutesForTraining > 90) {
						pMinutesForTraining = 90;
					}
					pTrainingPoints = ((trPoint.getTrainingPoint(trainingType, 
							new Integer(TrainingPosition.keeper))) * (pMinutesForTraining / 90d));
				}
				if (pMinutesForTraining < 90) {
					tmp = pMinutesPlayedAsCD;
					if (tmp > 0) {
						if (pMinutesForTraining + tmp > 90) {
							tmp = 90 - pMinutesForTraining;
						}
						pMinutesForTraining += tmp;
						pTrainingPoints += ((trPoint.getTrainingPoint(trainingType, 
								new Integer(TrainingPosition.centralDefender))) * (tmp / 90d));
					}
					if (pMinutesForTraining < 90) {
						tmp = pMinutesPlayedAsWB;
						if (tmp > 0) {
							if (pMinutesForTraining + tmp > 90) {
								tmp = 90 - pMinutesForTraining;
							}
							pMinutesForTraining += tmp;
							pTrainingPoints +=  ((trPoint.getTrainingPoint(trainingType, 
									new Integer(TrainingPosition.wingBack))) * (tmp / 90d));
						}
						if (pMinutesForTraining < 90) {
							tmp = pMinutesPlayedAsW;
							if (tmp > 0) {
								if (pMinutesForTraining + tmp > 90) {
									tmp = 90 - pMinutesForTraining;
								}
								pMinutesForTraining += tmp;
								pTrainingPoints += ((trPoint.getTrainingPoint(trainingType, 
										new Integer(TrainingPosition.winger))) * (tmp / 90d));
							}
							if (pMinutesForTraining < 90) {
								tmp = pMinutesPlayedAsIM;
								if (tmp > 0) {
									if (pMinutesForTraining + tmp > 90) {
										tmp = 90 - pMinutesForTraining;
									}
									pMinutesForTraining += tmp;
									pTrainingPoints += ((trPoint.getTrainingPoint(trainingType, 
											new Integer(TrainingPosition.innerMidfielder))) * (tmp / 90d));
								}
								if (pMinutesForTraining < 90) {
									tmp = pMinutesPlayedAsFW;
									if (tmp > 0) {
										if (pMinutesForTraining + tmp > 90) {
											tmp = 90 - pMinutesForTraining;
										}
										pMinutesForTraining += tmp;
										pTrainingPoints += ((trPoint.getTrainingPoint(trainingType, 
												new Integer(TrainingPosition.forward))) * (tmp / 90d));
									}
								}
							}
						}
					}
				}
				break;
			case TrainingType.CROSSING_WINGER:
				pMinutesForTraining = pMinutesPlayedAsW;
				if (pMinutesForTraining > 0) {
					if (pMinutesForTraining > 90) {
						pMinutesForTraining = 90;
					}
					pTrainingPoints = ((trPoint.getTrainingPoint(trainingType, 
							new Integer(TrainingPosition.winger))) * (pMinutesForTraining / 90d));
				}
				if (pMinutesForTraining < 90) {
					tmp = pMinutesPlayedAsWB;
					if (tmp > 0) {
						if (pMinutesForTraining + tmp > 90) {
							tmp = 90 - pMinutesForTraining;
						}
						pMinutesForTraining += tmp;
						pTrainingPoints += ((trPoint.getTrainingPoint(trainingType, 
								new Integer(TrainingPosition.wingBack))) * (tmp / 90d));
					}
					if (pMinutesForTraining < 90)
					{
						tmp = pMinutesPlayedAsGK + pMinutesPlayedAsCD + pMinutesPlayedAsIM + pMinutesPlayedAsFW;
						if (tmp > 0) {
							if (pMinutesForTraining + tmp > 90) {
								tmp = 90 - pMinutesForTraining;
							}
							pMinutesForTraining += tmp;
							pTrainingPoints += ((trPoint.getTrainingPoint(trainingType, 
									new Integer(TrainingPosition.osmosis))) * (tmp / 90d));
						}
					}
				}
				break;
			case TrainingType.WING_ATTACKS:
				pMinutesForTraining = pMinutesPlayedAsW;
				if (pMinutesForTraining > 0) {
					if (pMinutesForTraining > 90) {
						pMinutesForTraining = 90;
					}
					pTrainingPoints = ((trPoint.getTrainingPoint(trainingType, 
							new Integer(TrainingPosition.winger))) * (pMinutesForTraining / 90d));
				}
				if (pMinutesForTraining < 90) {
					tmp = pMinutesPlayedAsFW;
					if (tmp > 0) {
						if (pMinutesForTraining + tmp > 90) {
							tmp = 90 - pMinutesForTraining;
						}
						pMinutesForTraining += tmp;
						pTrainingPoints += ((trPoint.getTrainingPoint(trainingType, 
								new Integer(TrainingPosition.forward))) * (tmp / 90d));
					}
					if (pMinutesForTraining < 90) {
						tmp = pMinutesPlayedAsGK + pMinutesPlayedAsCD + pMinutesPlayedAsWB + pMinutesPlayedAsIM;
						if (tmp > 0) {
							if (pMinutesForTraining + tmp > 90) {
								tmp = 90 - pMinutesForTraining;
							}
							pMinutesForTraining += tmp;
							pTrainingPoints += ((trPoint.getTrainingPoint(trainingType, 
									new Integer(TrainingPosition.osmosis))) * (tmp / 90d));
						}
					}
				}
				break;
			case TrainingType.PLAYMAKING:
				pMinutesForTraining = pMinutesPlayedAsIM;
				if (pMinutesForTraining > 0) {
					if (pMinutesForTraining > 90) {
						pMinutesForTraining = 90;
					}
					pTrainingPoints = ((trPoint.getTrainingPoint(trainingType, 
							new Integer(TrainingPosition.innerMidfielder))) * (pMinutesForTraining / 90d));
				}
				if (pMinutesForTraining < 90) {
					tmp = pMinutesPlayedAsW;
					if (tmp > 0) {
						if (pMinutesForTraining + tmp > 90) {
							tmp = 90 - pMinutesForTraining;
						}
						pMinutesForTraining += tmp;
						pTrainingPoints += ((trPoint.getTrainingPoint(trainingType, 
								new Integer(TrainingPosition.winger))) * (tmp / 90d));
					}
					if (pMinutesForTraining < 90) {
						tmp = pMinutesPlayedAsGK + pMinutesPlayedAsCD + pMinutesPlayedAsWB + pMinutesPlayedAsFW;
						if (tmp > 0) {
							if (pMinutesForTraining + tmp > 90) {
								tmp = 90 - pMinutesForTraining;
							}
							pMinutesForTraining += tmp;
							pTrainingPoints += ((trPoint.getTrainingPoint(trainingType, 
									new Integer(TrainingPosition.osmosis))) * (tmp / 90d));
						}
					}
				}
				break;
			case TrainingType.SHORT_PASSES:
				pMinutesForTraining = pMinutesPlayedAsIM;
				if (pMinutesForTraining > 0) {
					if (pMinutesForTraining > 90) {
						pMinutesForTraining = 90;
					}
					pTrainingPoints = ((trPoint.getTrainingPoint(trainingType, 
							new Integer(TrainingPosition.innerMidfielder))) * (pMinutesForTraining / 90d));
				}
				if (pMinutesForTraining < 90) {
					tmp = pMinutesPlayedAsW;
					if (tmp > 0) {
						if (pMinutesForTraining + tmp > 90) {
							tmp = 90 - pMinutesForTraining;
						}
						pMinutesForTraining += tmp;
						pTrainingPoints += ((trPoint.getTrainingPoint(trainingType, 
								new Integer(TrainingPosition.winger))) * (tmp / 90d));
					}
					if (pMinutesForTraining < 90) {
						tmp = pMinutesPlayedAsFW;
						if (tmp > 0) {
							if (pMinutesForTraining + tmp > 90) {
								tmp = 90 - pMinutesForTraining;
							}
							pMinutesForTraining += tmp;
							pTrainingPoints += ((trPoint.getTrainingPoint(trainingType, 
									new Integer(TrainingPosition.forward))) * (tmp / 90d));
						}
						if (pMinutesForTraining < 90) {
							tmp = pMinutesPlayedAsGK + pMinutesPlayedAsWB + pMinutesPlayedAsCD;
							if (tmp > 0) {
								if (pMinutesForTraining + tmp > 90) {
									tmp = 90 - pMinutesForTraining;
								}
								pMinutesForTraining += tmp;
								pTrainingPoints += ((trPoint.getTrainingPoint(trainingType, 
										new Integer(TrainingPosition.osmosis))) * (tmp / 90d));
							}
						}
					}
				}
				break;
			case TrainingType.THROUGH_PASSES:
				pMinutesForTraining = pMinutesPlayedAsWB;
				if (pMinutesForTraining > 0) {
					if (pMinutesForTraining > 90) {
						pMinutesForTraining = 90;
					}
					pTrainingPoints = ((trPoint.getTrainingPoint(trainingType, 
							new Integer(TrainingPosition.wingBack))) * (pMinutesForTraining / 90d));
				}
				if (pMinutesForTraining < 90) {
					tmp = pMinutesPlayedAsCD;
					if (tmp > 0) {
						if (pMinutesForTraining + tmp > 90) {
							tmp = 90 - pMinutesForTraining;
						}
						pMinutesForTraining += tmp;
						pTrainingPoints += ((trPoint.getTrainingPoint(trainingType, 
								new Integer(TrainingPosition.centralDefender))) * (tmp / 90d));
					}
					if (pMinutesForTraining < 90) {
						tmp = pMinutesPlayedAsW;
						if (tmp > 0) {
							if (pMinutesForTraining + tmp > 90) {
								tmp = 90 - pMinutesForTraining;
							}
							pMinutesForTraining += tmp;
							pTrainingPoints += ((trPoint.getTrainingPoint(trainingType, 
									new Integer(TrainingPosition.winger))) * (tmp / 90d));
						}
						if (pMinutesForTraining < 90) {
							tmp = pMinutesPlayedAsIM;
							if (tmp > 0) {
								if (pMinutesForTraining + tmp > 90) {
									tmp = 90 - pMinutesForTraining;
								}
								pMinutesForTraining += tmp;
								pTrainingPoints += ((trPoint.getTrainingPoint(trainingType, 
										new Integer(TrainingPosition.innerMidfielder))) * (tmp / 90d));
							}
							if (pMinutesForTraining < 90) {
								tmp = pMinutesPlayedAsGK + pMinutesPlayedAsFW;
								if (tmp > 0) {
									if (pMinutesForTraining + tmp > 90) {
										tmp = 90 - pMinutesForTraining;
									}
									pMinutesForTraining += tmp;
									pTrainingPoints += ((trPoint.getTrainingPoint(trainingType, 
											new Integer(TrainingPosition.osmosis))) * (tmp / 90d));
								}
							}
						}
					}
				}
				break;
			case TrainingType.SCORING:
				pMinutesForTraining = pMinutesPlayedAsFW;
				if (pMinutesForTraining > 0) {
					if (pMinutesForTraining > 90) {
						pMinutesForTraining = 90;
					}
					pTrainingPoints = ((trPoint.getTrainingPoint(trainingType, 
							new Integer(TrainingPosition.forward))) * (pMinutesForTraining / 90d));
				}
				if (pMinutesForTraining < 90) {
					tmp = pMinutesPlayedAsGK + pMinutesPlayedAsWB + pMinutesPlayedAsCD + pMinutesPlayedAsW + pMinutesPlayedAsFW;
					if (tmp > 0) {
						if (pMinutesForTraining + tmp > 90) {
							tmp = 90 - pMinutesForTraining;
						}
						pMinutesForTraining += tmp;
						pTrainingPoints += ((trPoint.getTrainingPoint(trainingType, 
								new Integer(TrainingPosition.osmosis))) * (tmp / 90d));
					}
				}
				break;
			case TrainingType.SHOOTING:
				pMinutesForTraining = pMinutesPlayedAsGK;
				if (pMinutesForTraining > 0) {
					if (pMinutesForTraining > 90) {
						pMinutesForTraining = 90;
					}
					pTrainingPoints = ((trPoint.getTrainingPoint(trainingType, 
							new Integer(TrainingPosition.keeper))) * (pMinutesForTraining / 90d));
				}
				if (pMinutesForTraining < 90) {
					tmp = pMinutesPlayedAsWB;
					if (tmp > 0) {
						if (pMinutesForTraining + tmp > 90) {
							tmp = 90 - pMinutesForTraining;
						}
						pMinutesForTraining += tmp;
						pTrainingPoints += ((trPoint.getTrainingPoint(trainingType, 
								new Integer(TrainingPosition.wingBack))) * (tmp / 90d));
					}
					if (pMinutesForTraining < 90) {
						tmp = pMinutesPlayedAsCD;
						if (tmp > 0) {
							if (pMinutesForTraining + tmp > 90) {
								tmp = 90 - pMinutesForTraining;
							}
							pMinutesForTraining += tmp;
							pTrainingPoints += ((trPoint.getTrainingPoint(trainingType, 
									new Integer(TrainingPosition.centralDefender))) * (tmp / 90d));
						}
						if (pMinutesForTraining < 90) {
							tmp = pMinutesPlayedAsW;
							if (tmp > 0) {
								if (pMinutesForTraining + tmp > 90) {
									tmp = 90 - pMinutesForTraining;
								}
								pMinutesForTraining += tmp;
								pTrainingPoints += ((trPoint.getTrainingPoint(trainingType, 
										new Integer(TrainingPosition.winger))) * (tmp / 90d));
							}
							if (pMinutesForTraining < 90) {
								tmp = pMinutesPlayedAsIM;
								if (tmp > 0) {
									if (pMinutesForTraining + tmp > 90) {
										tmp = 90 - pMinutesForTraining;
									}
									pMinutesForTraining += tmp;
									pTrainingPoints += ((trPoint.getTrainingPoint(trainingType, 
											new Integer(TrainingPosition.innerMidfielder))) * (tmp / 90d));
								}
								if (pMinutesForTraining < 90) {
									tmp = pMinutesPlayedAsFW;
									if (tmp > 0) {
										if (pMinutesForTraining + tmp > 90) {
											tmp = 90 - pMinutesForTraining;
										}
										pMinutesForTraining += tmp;
										pTrainingPoints += ((trPoint.getTrainingPoint(trainingType, 
												new Integer(TrainingPosition.forward))) * (tmp / 90d));
									}
								}
							}
						}
					}
				}
				break;
			case TrainingType.SET_PIECES:
				pMinutesForTraining = pMinutesPlayedAsGK;
				if (pMinutesForTraining > 0) {
					if (pMinutesForTraining > 90) {
						pMinutesForTraining = 90;
					}
					pTrainingPoints = ((trPoint.getTrainingPoint(trainingType, 
							new Integer(TrainingPosition.keeper))) * (pMinutesForTraining / 90d));
				}
				if (pMinutesForTraining < 90) {
					tmp = pMinutesPlayedAsWB;
					if (tmp > 0) {
						if (pMinutesForTraining + tmp > 90) {
							tmp = 90 - pMinutesForTraining;
						}
						pMinutesForTraining += tmp;
						pTrainingPoints += ((trPoint.getTrainingPoint(trainingType, 
								new Integer(TrainingPosition.wingBack))) * (tmp / 90d));
					}
					if (pMinutesForTraining < 90) {
						tmp = pMinutesPlayedAsCD;
						if (tmp > 0) {
							if (pMinutesForTraining + tmp > 90) {
								tmp = 90 - pMinutesForTraining;
							}
							pMinutesForTraining += tmp;
							pTrainingPoints += ((trPoint.getTrainingPoint(trainingType, 
									new Integer(TrainingPosition.centralDefender))) * (tmp / 90d));
						}
						if (pMinutesForTraining < 90) {
							tmp = pMinutesPlayedAsW;
							if (tmp > 0) {
								if (pMinutesForTraining + tmp > 90) {
									tmp = 90 - pMinutesForTraining;
								}
								pMinutesForTraining += tmp;
								pTrainingPoints += ((trPoint.getTrainingPoint(trainingType, 
										new Integer(TrainingPosition.winger))) * (tmp / 90d));
							}
							if (pMinutesForTraining < 90) {
								tmp = pMinutesPlayedAsIM;
								if (tmp > 0) {
									if (pMinutesForTraining + tmp > 90) {
										tmp = 90 - pMinutesForTraining;
									}
									pMinutesForTraining += tmp;
									pTrainingPoints += ((trPoint.getTrainingPoint(trainingType, 
											new Integer(TrainingPosition.innerMidfielder))) * (tmp / 90d));
								}
								if (pMinutesForTraining < 90) {
									tmp = pMinutesPlayedAsFW;
									if (tmp > 0) {
										if (pMinutesForTraining + tmp > 90) {
											tmp = 90 - pMinutesForTraining;
										}
										pMinutesForTraining += tmp;
										pTrainingPoints += ((trPoint.getTrainingPoint(trainingType, 
												new Integer(TrainingPosition.forward))) * (tmp / 90d));
									}
								}
							}
						}
					}
				}
				tmp = pMinutesPlayedAsSP;
				// Don't add to minutes played, as it's already added elsewhere.
				if (tmp > 0) {
					pTrainingPoints += ((trPoint.getTrainingPoint(trainingType, 
							new Integer(TrainingPosition.setPiece))) * (tmp / 90d));
				}
				break;
		}
	}
}
