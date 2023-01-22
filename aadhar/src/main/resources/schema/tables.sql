CREATE TABLE `id_policy_entity` (
                                    `id` int(11) NOT NULL,
                                    `counter` bigint(20) NOT NULL,
                                    `created_on` datetime DEFAULT NULL,
                                    `description` varchar(255) DEFAULT NULL,
                                    `fetch_size` int(11) NOT NULL,
                                    `id_type` int(11) NOT NULL,
                                    `last_modified_on` datetime DEFAULT NULL,
                                    PRIMARY KEY (`id`)
);

create table hibernate_sequence (
    next_val bigint
);

insert into hibernate_sequence values ( 1 );
