<?php
class Connexion {
    
    private $host      = "127.0.0.1";
    private $user      = "root";
    private $pass      = "";
    private $dbname    = "thewalkinggame";
    private static $connexion;
    
    /**
     * @var PDOStatement
     */
    private $stmt;
 
    /**
     * @var PDO
     */
    private $bdd;
 
    /**
     * Create a new PDO instanace
     */
    private function Connexion($host="127.0.0.1", $dbname = "thewalkinggame", $user="root", $pass="")
    {
        try{
            $this->host = $host;
            $this->dbname = $dbname;
            $this->user = $user;
            $this->pass = $pass;
            $this->bdd = new PDO('mysql:host=' . $this->host . ';dbname=' . $this->dbname, $this->user, $this->pass);
        }
        // Catch any errors
        catch(PDOException $e){
            die('Erreur lors de la connexion avec la BD'.$e->getMessage());
        }
    }
    
    public static function getConnexion($host="127.0.0.1", $dbname = "thewalkinggame", $user="root", $pass=""){
        if(self::$connexion == null){
            self::$connexion = new Connexion($host, $dbname, $user, $pass);
        }
        return self::$connexion;
    }
    
    /**
     * Allows the user to send a sql query thanks to PDO
     * @param String $query
     */
    public function  query($query)
    {
        return $this->bdd->query($query);
    }
    
    /**
     * This Function prepare a statement. After this function
     * the user has to bind the parameters for his request
     * @param String $query
     */
    public function prepare($query){
        $this->stmt = $this->bdd->prepare($query);
    }
    
    /**
     * This function allows to binds parameters for the sql request
     * @param string $param
     * @param $value
     * @param string $type
     */
    public function bind($param, $value, $type = null){
        if (is_null($type)) {
            switch (true) {
                case is_int($value):
                    $type = PDO::PARAM_INT;
                    break;
                case is_bool($value):
                    $type = PDO::PARAM_BOOL;
                    break;
                case is_null($value):
                    $type = PDO::PARAM_NULL;
                    break;
                default:
                    $type = PDO::PARAM_STR;
            }
        }
        $this->stmt->bindValue($param, $value, $type);
    }
    
    /**
     * This function execute a Statement
     */
    public function execute(){
        return $this->stmt->execute();
    }
    
    /**
     * This function fetch one result
     */
    public function fetch(){
        $this->execute();
        return $this->stmt->fetch(PDO::FETCH_ASSOC);
    }
    
    /**
     * This function returns an array of result
     * @return array:
     */
    public function fetchAll(){
        $this->execute();
        return $this->stmt->fetchAll(PDO::FETCH_ASSOC);
    }
    
    /**
     * Count the number of results
     */
    public function rowCount(){
        return $this->stmt->rowCount();
    }
}
?>