CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
------------------------------------------------------------
CREATE OR REPLACE FUNCTION deleteUserData() RETURNS TRIGGER AS
$$
BEGIN
    DELETE FROM tags WHERE "userUUID" = OLD."userUUID";
    DELETE FROM pressurerecords WHERE "userUUID" = OLD."userUUID";
    DELETE FROM notifications WHERE "userUUID" = OLD."userUUID";
    DELETE FROM devices WHERE "userUUID" = OLD."userUUID";
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS triggerDeleteUserData ON users;

CREATE TRIGGER triggerDeleteUserData
    BEFORE DELETE
    ON users
    FOR EACH ROW
EXECUTE FUNCTION deleteUserData();
------------------------------------------------------------
CREATE OR REPLACE FUNCTION deletePressureTagsLinkBeforeTag() RETURNS TRIGGER AS
$$
BEGIN
    DELETE FROM pressurerecordtaglinkstable WHERE "tagUUID" = OLD."tagUUID";
    RETURN OLD;
END
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS triggerDeletePressureTagsLinkBeforeTag ON tags;

CREATE TRIGGER triggerDeletePressureTagsLinkBeforeTag
    BEFORE DELETE
    ON tags
    FOR EACH ROW
EXECUTE FUNCTION deletePressureTagsLinkBeforeTag();

------------------------------------------------------------
CREATE OR REPLACE FUNCTION deletePressureTagsLinkBeforePressure() RETURNS TRIGGER AS
$$
BEGIN
    DELETE
    FROM pressurerecordtaglinkstable
    WHERE "pressureRecordUUID" IN
          (SELECT pressurerecords."pressureRecordUUID" FROM pressurerecords WHERE "userUUID" = OLD."userUUID");
    RETURN OLD;
END
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS triggerDeletePressureTagsLinkBeforePressure ON pressurerecords;

CREATE TRIGGER triggerDeletePressureTagsLinkBeforePressure
    BEFORE DELETE
    ON pressurerecords
    FOR EACH ROW
EXECUTE FUNCTION deletePressureTagsLinkBeforePressure();
------------------------------------------------------------
CREATE OR REPLACE FUNCTION deleteHistoryBeforePressureRecord() RETURNS TRIGGER AS
$$
BEGIN
    DELETE
    FROM history
    WHERE "pressureRecordUUID" = OLD."pressureRecordUUID";
    RETURN OLD;
END
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS triggerDeleteHistoryBeforePressureRecord ON pressurerecords;

CREATE TRIGGER triggerDeleteHistoryBeforePressureRecord
    BEFORE DELETE
    ON pressurerecords
    FOR EACH ROW
EXECUTE FUNCTION deleteHistoryBeforePressureRecord();
------------------------------------------------------------
CREATE OR REPLACE FUNCTION deleteOldestHistoryEntry()
    RETURNS TRIGGER AS
$$
BEGIN
    IF (SELECT COUNT(*) FROM history WHERE "pressureRecordUUID" = NEW."pressureRecordUUID") > 5 THEN
        DELETE
        FROM history
        WHERE "historyUUID" = (SELECT "historyUUID"
                               FROM history
                               WHERE "pressureRecordUUID" = NEW."pressureRecordUUID"
                               ORDER BY "dateTimeModified"
                               LIMIT 1);
    END IF;
    RETURN NEW;
END
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS triggerDeleteOldestHistory ON history;

CREATE TRIGGER triggerDeleteOldestHistory
    AFTER INSERT
    ON history
    FOR EACH ROW
EXECUTE FUNCTION deleteOldestHistoryEntry();
------------------------------------------------------------
CREATE OR REPLACE FUNCTION updateOrInsertDevice()
    RETURNS TRIGGER AS
$$
BEGIN
    IF EXISTS (SELECT 1
               FROM devices
               WHERE "userUUID" = NEW."userUUID"
                 AND "deviceType" = NEW."deviceType") THEN
        UPDATE devices
        SET "lastSyncDate" = CURRENT_TIMESTAMP
        WHERE "userUUID" = NEW."userUUID"
          AND "deviceType" = NEW."deviceType";
    ELSE
        INSERT INTO devices ("deviceUUID", "userUUID", "deviceType", "lastSyncDate")
        VALUES (uuid_generate_v4(), NEW."userUUID", NEW."deviceType", CURRENT_TIMESTAMP);
    END IF;
    RETURN NEW;
END
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS triggerUpdateOrInsertDevice ON pressurerecords;

CREATE TRIGGER triggerUpdateOrInsertDevice
    AFTER INSERT
    ON pressurerecords
    FOR EACH ROW
EXECUTE FUNCTION updateOrInsertDevice();
------------------------------------------------------------

