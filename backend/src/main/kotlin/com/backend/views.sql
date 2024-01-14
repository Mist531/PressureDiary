CREATE OR REPLACE VIEW "UserLatestPressureRecord" AS
SELECT "u"."userUUID",
       "u"."email",
       MAX("pr"."dateTimeRecord") as "LatestRecordTime",
       "pr"."systolic",
       "pr"."diastolic"
FROM Users "u"
         JOIN PressureRecords "pr" ON "u"."userUUID" = "pr"."userUUID"
GROUP BY "u"."userUUID", "pr"."systolic", "pr"."diastolic";
------------------------------------------------------------
CREATE OR REPLACE VIEW "UserDeviceCount" AS
SELECT "u"."userUUID", "u"."email", COUNT("d"."deviceUUID") as "DeviceCount"
FROM Users "u"
         LEFT JOIN Devices "d" ON "u"."userUUID" = "d"."userUUID"
GROUP BY "u"."userUUID";
------------------------------------------------------------
CREATE OR REPLACE VIEW "UserNextNotification" AS
SELECT "u"."userUUID", "u"."email", MIN("n"."timeToSend") as "NextNotificationTime"
FROM Users "u"
         LEFT JOIN Notifications "n" ON "u"."userUUID" = "n"."userUUID"
GROUP BY "u"."userUUID";
------------------------------------------------------------
CREATE OR REPLACE VIEW "TagPressureRecordLink" AS
SELECT "t"."tagUUID", "t"."name", "pr"."pressureRecordUUID", "pr"."dateTimeRecord"
FROM Tags "t"
         LEFT JOIN Pressurerecordtaglinkstable "p" ON "t"."tagUUID" = "p"."tagUUID"
         LEFT JOIN PressureRecords "pr" ON "p"."pressureRecordUUID" = "pr"."pressureRecordUUID";
------------------------------------------------------------
CREATE OR REPLACE VIEW "DeviceLastSync" AS
SELECT "d"."deviceUUID", "d"."deviceType", MAX("pr"."dateTimeRecord") as "LastSyncTime"
FROM Devices "d"
         LEFT JOIN PressureRecords "pr" ON "d"."userUUID" = "pr"."userUUID"
GROUP BY "d"."deviceUUID", "d"."deviceType";
