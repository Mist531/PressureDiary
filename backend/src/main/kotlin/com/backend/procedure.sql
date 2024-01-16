CREATE OR REPLACE PROCEDURE add_device(user_id UUID, device_type INT)
    LANGUAGE plpgsql AS
$$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM devices WHERE "userUUID" = user_id AND "deviceType" = device_type) THEN
        INSERT INTO devices ("deviceUUID", "userUUID", "deviceType", "lastSyncDate")
        VALUES (public.uuid_generate_v4(), user_id, device_type, CURRENT_TIMESTAMP);
    END IF;
END;
$$;
--------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE update_notification(notification_id UUID, new_message TEXT, new_time TIMESTAMPTZ)
    LANGUAGE plpgsql AS
$$
BEGIN
    UPDATE notifications
    SET message      = new_message,
        "timeToSend" = new_time
    WHERE "notificationUUID" = notification_id;
END;
$$;
--------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE deletePressureHistory(user_id UUID, older_than_date DATE)
    LANGUAGE plpgsql AS
$$
BEGIN
    DELETE
    FROM history
    WHERE "pressureRecordUUID" IN (SELECT "pressureRecordUUID" FROM pressurerecords WHERE "userUUID" = user_id)
      AND "dateTimeModified" < older_than_date;
END;
$$;