
library(lme4);
library(broom);

dataframe = vignettes;
#length(dataframe$risk)
dataframe$risk3=factor(dataframe$risk,c("family","workplace","city","state","country"));
dataframe$risk2=factor(dataframe$risk,c("only 1 person in your state","only 1 person in your family","only 1 person in your city","only 1 person in your workplace","only 1 person in your country"));

#dataframe$info2=factor(dataframe$infoType,c("AgeRange","FullName","HomeAddress","PhoneNumber","CreditCardNumber","DriversLicenseInformation"))
#dataframe$info3=factor(dataframe$infoType,c("AgeRange","Name","Address","PhoneNo","CreditCardNo","DLinfo"))
#dataframe$race=factor(dataframe$ethnicity,c("White","Asian/Pacific Islander","Black or African American", "Hispanic or Latino"
#                                            ,"Native American or American Indian","Other" ))
#dataframe$harm2=factor(dataframe$harm,c("Insecurity","Surveillance","InducedDisclosure"))
levels(dataframe$population)
#boxplot(split(dataframe$willingness,dataframe$population));
#boxplot(split(dataframe$willingness,dataframe$risk3));                                            
#boxplot(split(dataframe$willingness,dataframe$race));
#boxplot(split(dataframe$willingness,dataframe$harm));
boxplot(split(dataframe$willingness,dataframe$infoType));
#boxplot(split(dataframe$willingness,dataframe$cond));
#boxplot(split(dataframe$willingness,dataframe$shopping));
levels(dataframe$ethnicity);
#mod=aov(dataframe$willingness ~ (dataframe$infotype+dataframe$event+dataframe$risk+dataframe$vagueness)+Error(dataframe$workerID/(dataframe$vagueness)));
#coefs <- data.frame(coef(summary(mod)));
#coefs$p.z <- 2 * (1 - pnorm(abs(coefs$p.value)))
#coefs

#dataframe$risk2=factor(dataframe$risk,c("1 out of 4","1 out of 10","1 out of 100","1 out of 1000"));

M.model = lmer(willingness ~ dataframe$risk2+infoType+cond+ (1| workerID), data=dataframe, REML=FALSE);
M1.model = lmer(willingness ~ dataframe$infoType+risk+(1| workerID), data=dataframe, REML=FALSE);
M2.model = lmer(willingness ~ dataframe$risk2+info2+cond+population+(1| workerID), data=dataframe, REML=FALSE);
#M3.model = lmer(willingness ~ dataframe$risk2+info2+cond+(1| workerID), data=dataframe, REML=FALSE);
#MI.model = lmer(willingness ~ dataframe$risk2*harm+risk2*infoType+harm*infoType+ (1| workerID), data=dataframe, REML=FALSE);
#M1.model = lmer(willingness ~ shopping*dataframe$risk2*vagueness+ (1| workerID), data=dataframe, REML=FALSE);
M.null = lmer(willingness ~   1 + (1 | workerID), data=dataframe, REML=FALSE);

anova(M.null, M1.model);
#anova(M.model, M2.model);
summary(M.model);
coefs <- data.frame(coef(summary(M1.model)));
coefs$p.z <- 2 * (1 - pnorm(abs(coefs$t.value)))
coefs
tidy_lmfit <- tidy(M.model);
tidy_lmfit;
write.csv(tidy_lmfit, "tidy_lmfit.csv")
write(coefs,file="MLM-1.csv",append=TRUE)
coefs