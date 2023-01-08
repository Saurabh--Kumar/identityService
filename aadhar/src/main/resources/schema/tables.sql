create table id_policy_entity (
                                  id integer not null,
                                  counter bigint not null,
                                  created_on datetime,
                                  description varchar(255),
                                  fetch_size integer not null,
                                  id_type integer not null,
                                  last_modified_on datetime,
                                  primary key (id)
) engine=MyISAM;

create table hibernate_sequence (
    next_val bigint
) engine=MyISAM;
