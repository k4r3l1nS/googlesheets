CREATE OR REPLACE VIEW V_ITEM_DATA
AS
SELECT
    I.id,
    I.clothing_type,
    I.name,
    I.selling_price,
    ic.storage_url as icon
FROM Item I
LEFT JOIN icon ic on ic.id = I.icon_id
WHERE I.is_active = true
