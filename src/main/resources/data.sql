INSERT INTO subscription_action_type (action_name, description, display_template)
VALUES
    ('LIKE', '点赞操作', '{{user}} 点赞了你的内容'),
    ('COMMENT', '评论操作', '{{user}} 评论了你的内容'),
    ('REPLY', '回复操作', '{{user}} 回复了你的内容')
    ON DUPLICATE KEY UPDATE
                         description = VALUES(description),
                         display_template = VALUES(display_template);

INSERT IGNORE INTO subscription_config_meta_data (action, default_allow)
SELECT subscription_action_type_id, true
FROM subscription_action_type;