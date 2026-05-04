-- Suppression de la table log si elle existe déjà
DROP TABLE IF EXISTS log;

-- Création de la table log avec colonne utilisateur
CREATE TABLE log (
                     idLog SERIAL PRIMARY KEY,
                     tableName VARCHAR(50),
                     operation VARCHAR(50),
                     utilisateur VARCHAR(100),
                     dateAction TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                     ancienContenu TEXT,
                     nouveauContenu TEXT
);

-- Fonction générique d'insertion dans le log
CREATE OR REPLACE FUNCTION insert_log_function(
    p_tableName VARCHAR,
    p_operation VARCHAR,
    p_utilisateur VARCHAR,
    p_ancienContenu TEXT,
    p_nouveauContenu TEXT
) RETURNS void LANGUAGE plpgsql AS $$
BEGIN
INSERT INTO log (tableName, operation, utilisateur, ancienContenu, nouveauContenu)
VALUES (p_tableName, p_operation, p_utilisateur, p_ancienContenu, p_nouveauContenu);
END;
$$;

-- =====================
-- TRIGGERS FRANCHISE
-- =====================

-- INSERT
CREATE OR REPLACE FUNCTION trigger_franchise_create() RETURNS TRIGGER AS $$
DECLARE
v_utilisateur VARCHAR;
BEGIN
    -- Récupération du nom de l'utilisateur connecté passé depuis Java
BEGIN
        v_utilisateur := current_setting('myapp.utilisateur');
EXCEPTION WHEN OTHERS THEN
        v_utilisateur := 'inconnu';
END;

    PERFORM insert_log_function(
        'franchise',
        'INSERT',
        v_utilisateur,
        '',
        'ID: ' || NEW.id_franchise || ', Nom: ' || NEW.nom_franchise || ', Siege: ' || COALESCE(NEW.siege_social, '')
    );
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER franchise_create
    AFTER INSERT ON franchise
    FOR EACH ROW EXECUTE FUNCTION trigger_franchise_create();

-- UPDATE
CREATE OR REPLACE FUNCTION trigger_franchise_update() RETURNS TRIGGER AS $$
DECLARE
v_utilisateur VARCHAR;
BEGIN
BEGIN
        v_utilisateur := current_setting('myapp.utilisateur');
EXCEPTION WHEN OTHERS THEN
        v_utilisateur := 'inconnu';
END;

    PERFORM insert_log_function(
        'franchise',
        'UPDATE',
        v_utilisateur,
        'ID: ' || OLD.id_franchise || ', Nom: ' || OLD.nom_franchise || ', Siege: ' || COALESCE(OLD.siege_social, ''),
        'ID: ' || NEW.id_franchise || ', Nom: ' || NEW.nom_franchise || ', Siege: ' || COALESCE(NEW.siege_social, '')
    );
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER franchise_update
    AFTER UPDATE ON franchise
    FOR EACH ROW EXECUTE FUNCTION trigger_franchise_update();

-- DELETE
CREATE OR REPLACE FUNCTION trigger_franchise_delete() RETURNS TRIGGER AS $$
DECLARE
v_utilisateur VARCHAR;
BEGIN
BEGIN
        v_utilisateur := current_setting('myapp.utilisateur');
EXCEPTION WHEN OTHERS THEN
        v_utilisateur := 'inconnu';
END;

    PERFORM insert_log_function(
        'franchise',
        'DELETE',
        v_utilisateur,
        'ID: ' || OLD.id_franchise || ', Nom: ' || OLD.nom_franchise || ', Siege: ' || COALESCE(OLD.siege_social, ''),
        ''
    );
RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER franchise_delete
    AFTER DELETE ON franchise
    FOR EACH ROW EXECUTE FUNCTION trigger_franchise_delete();

-- =====================
-- TRIGGERS CINEMA
-- =====================

-- INSERT
CREATE OR REPLACE FUNCTION trigger_cinema_create() RETURNS TRIGGER AS $$
DECLARE
v_utilisateur VARCHAR;
BEGIN
BEGIN
        v_utilisateur := current_setting('myapp.utilisateur');
EXCEPTION WHEN OTHERS THEN
        v_utilisateur := 'inconnu';
END;

    PERFORM insert_log_function(
        'cinema',
        'INSERT',
        v_utilisateur,
        '',
        'ID: ' || NEW.id_cinema || ', Denomination: ' || NEW.denomination || ', Ville: ' || COALESCE(NEW.ville, '')
    );
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER cinema_create
    AFTER INSERT ON cinema
    FOR EACH ROW EXECUTE FUNCTION trigger_cinema_create();

-- UPDATE
CREATE OR REPLACE FUNCTION trigger_cinema_update() RETURNS TRIGGER AS $$
DECLARE
v_utilisateur VARCHAR;
BEGIN
BEGIN
        v_utilisateur := current_setting('myapp.utilisateur');
EXCEPTION WHEN OTHERS THEN
        v_utilisateur := 'inconnu';
END;

    PERFORM insert_log_function(
        'cinema',
        'UPDATE',
        v_utilisateur,
        'ID: ' || OLD.id_cinema || ', Denomination: ' || OLD.denomination || ', Ville: ' || COALESCE(OLD.ville, ''),
        'ID: ' || NEW.id_cinema || ', Denomination: ' || NEW.denomination || ', Ville: ' || COALESCE(NEW.ville, '')
    );
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER cinema_update
    AFTER UPDATE ON cinema
    FOR EACH ROW EXECUTE FUNCTION trigger_cinema_update();

-- DELETE
CREATE OR REPLACE FUNCTION trigger_cinema_delete() RETURNS TRIGGER AS $$
DECLARE
v_utilisateur VARCHAR;
BEGIN
BEGIN
        v_utilisateur := current_setting('myapp.utilisateur');
EXCEPTION WHEN OTHERS THEN
        v_utilisateur := 'inconnu';
END;

    PERFORM insert_log_function(
        'cinema',
        'DELETE',
        v_utilisateur,
        'ID: ' || OLD.id_cinema || ', Denomination: ' || OLD.denomination || ', Ville: ' || COALESCE(OLD.ville, ''),
        ''
    );
RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER cinema_delete
    AFTER DELETE ON cinema
    FOR EACH ROW EXECUTE FUNCTION trigger_cinema_delete();