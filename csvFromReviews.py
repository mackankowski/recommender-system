import mysql.connector

def main():
    cnx = mysql.connector.connect(user='root', password='prestashop', host='172.20.83.76', port=4444, database='prestashop')
    cursor = cnx.cursor()
    query = ("SELECT id_entity, id_customer, grade FROM ps_revws_review LEFT JOIN ps_revws_review_grade ON ps_revws_review.id_review = ps_revws_review_grade.id_review")
    cursor.execute(query)
    f = open("/home/student/biznes/recommender-system/target/table.csv", "w")    
    for (id_entity, id_customer, grade) in cursor:
        print("Product: {} was rated by: {} with score {}".format(id_entity, id_customer, grade))
        f.write("{},{},{}\n".format( id_customer, id_entity, grade))
    f.close()
    cnx.close()

if __name__ == '__main__':
    main()
