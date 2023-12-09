import { Column, Entity, PrimaryGeneratedColumn } from "typeorm";

@Entity()
export class User {

    @PrimaryGeneratedColumn()
    id: number;

    @Column({default:'blankName'})
    name: string;

    @Column({default:'blankEmail'})
    email: string;

    @Column({default:'blankPassword'})
    password: string;

    @Column({nullable: true, type: 'json'})
    favoriteAppIds: any; //ver como colocar array de appIds
    



}
