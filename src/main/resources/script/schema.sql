drop table if exists ACCOUNT CASCADE;
drop table if exists PERMISSION CASCADE;
drop table if exists PERMISSION_ROLE CASCADE;
drop table if exists ROLE CASCADE;
drop table if exists ROLE_user CASCADE;


create table ACCOUNT (id integer generated by default as identity,
account_expired boolean, account_locked boolean, credentials_expired boolean,
email varchar(255), enabled boolean not null, password varchar(255),
user_name varchar(255), primary key (id));

create table PERMISSION (id integer generated by default as identity, name varchar(255), primary key (id));

create table PERMISSION_ROLE (ROLE_id integer not null, PERMISSION_id integer not null);

create table ROLE (id integer generated by default as identity, name varchar(255), primary key (id));
create table ROLE_user (user_id integer not null, ROLE_id integer not null);
alter table PERMISSION_ROLE add constraint FK3tuvkbyi6wcytyg21hvpd6txw foreign key (PERMISSION_id) references PERMISSION;
alter table PERMISSION_ROLE add constraint FK50sfdcvbvdaclpn7wp4uop4ml foreign key (ROLE_id) references ROLE;
alter table ROLE_user add constraint FKiqpmjd2qb4rdkej916ymonic6 foreign key (ROLE_id) references ROLE;
alter table ROLE_user add constraint FKnw63micwfaof0c7narim1t3kh foreign key (user_id) references ACCOUNT;


drop table if exists oauth_client_details;
        create table oauth_client_details (
        client_id VARCHAR(255) PRIMARY KEY,
        resource_ids VARCHAR(255),
        client_secret VARCHAR(255),
        scope VARCHAR(255),
        authorized_grant_types VARCHAR(255),
        web_server_redirect_uri VARCHAR(255),
        authorities VARCHAR(255),
        access_token_validity INTEGER,
        refresh_token_validity INTEGER,
        additional_information VARCHAR(4096),
        autoapprove VARCHAR(255)
        );

drop table if exists oauth_client_token;
        create table oauth_client_token(
        token_id VARCHAR(255),
        token LONGVARBINARY,
        authentication_id VARCHAR(255) PRIMARY KEY,
        user_name VARCHAR(255),
        client_id VARCHAR(255)
        );

drop table if exists oauth_access_token;
        create table oauth_access_token(
        token_id VARCHAR(255),
        token LONGVARBINARY,
        authentication_id VARCHAR(255) PRIMARY KEY,
        user_name VARCHAR(255),
        client_id VARCHAR(255),
        authentication LONGVARBINARY,
        refresh_token VARCHAR(255)
        );
drop table if exists oauth_refresh_token;
        create table oauth_refresh_token (
        token_id VARCHAR(255),
        token LONGVARBINARY,
        authentication LONGVARBINARY
        );
drop table if exists oauth_code;
        create table oauth_code (
        code VARCHAR(255), authentication LONGVARBINARY
        );
drop table if exists oauth_approvals;
        create table oauth_approvals (
        userId VARCHAR(255),
        clientId VARCHAR(255),
        scope VARCHAR(255),
        status VARCHAR(10),
        expiresAt TIMESTAMP,
        lastModifiedAt TIMESTAMP
        );
drop table if exists ClientDetails;
        create table ClientDetails (
        appId VARCHAR(255) PRIMARY KEY,
        resourceIds VARCHAR(255),
        appSecret VARCHAR(255),
        scope VARCHAR(255),
        grantTypes VARCHAR(255),
        redirectUrl VARCHAR(255),
        authorities VARCHAR(255),
        access_token_validity INTEGER,
        refresh_token_validity INTEGER,
        additionalInformation VARCHAR(4096),
        autoApproveScopes VARCHAR(255)
        );