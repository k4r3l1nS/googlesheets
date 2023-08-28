CREATE OR REPLACE VIEW V_ITEM_DATA
AS
SELECT
    I.id,
    I.clothing_type,
    I.name,
    I.selling_price,
    ic.storage_url as main_icon
FROM Item I
LEFT JOIN item_icons ii on I.id = ii.items_id
LEFT JOIN icon ic on ii.icons_id = ic.id
WHERE I.is_active = true
AND (ic.is_main = true OR ic.storage_url is NULL)
