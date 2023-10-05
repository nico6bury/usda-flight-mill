
#***************************************************************************************************************************
# Creates a time list in which each element represents the occurence of a single peak event.
#***************************************************************************************************************************

def time_list(time, channel):
    time_channel=[]
    for i in range(0, len(channel)):
        if float(channel[i]) == 1.00:
            time_channel.append(float(time[i]))

    return time_channel


#***************************************************************************************************************************
# Creates a list in which each element represents the speed variation between successive peak events.
# The function needs to be modified in order to accommodate the circular flight path distance covered by the insect in flight.
# This depends on the mill's arm radius (i.e. the distance between the tethered insect and the rotational pivot) choosen by
# the user. In the example below the arm radius was set to 17.5cm which corresponds to a 1.0996m circular flight path.
#
# Due to the very low friction of the magnetic bearing, the mill's arm stops rotating some time after the insect ends
# its flying bout. This function includes an optional speed correction loop to account for these additional
# rotations that uses a threshold speed value below which the speed is set to 0. The threshold speed value needs to be
# choosen with care when working with slow flying insects.
#
# The function also automatically accounts for unused or empty channels and for instances in which only one flight
# event occurred.
#***************************************************************************************************************************

def speed_list(time, ch_number):
    ch = str(ch_number)
    speed_t=0
    speed_channel=[]
    speed_channel.append(0)
    if len(time) > 0:
        if len(time) > 2:
            for i in range(1, len(time)):
                if float(time[i]) != float(time[i-1]):
                    speed_t = 1.0996/(float(time[i]) - float(time[i-1]))
                    speed_channel.append(float(speed_t))
                else:
                    speed_channel.append(10)

            #*********************************************************************
            # Optional error correction.
            # Change the threshold speed value accordingly
            # Delete the # at the beginning of line 50-52 to activate the command
            #*********************************************************************
            
            #for x in range(0, len(speed_channel)):
            #    if float(speed_channel[x]) < 0.25:
            #        speed_channel[x] = 0

        else:
            print ("Channel ",ch, "has only one peak - impossible to calculate motion stats")
    else:
        print ("Channel ",ch, "is empty")
        
    return speed_channel

#***************************************************************************************************************************
# Calculate distance and average speed. The function corrects for false readings in the voltage signal which are identified
# by speed values higher than a certain threshold. Such threshold value can be modified by the user to account for fast
# flying insects. The function also accounts for very short gaps (7s in the example below) if these occurs between two
# consecutive long and ininterrupted flying bouts. Such gap value can be modified by the user.
#***************************************************************************************************************************

def distance(time, speed):
    distance=0
    average_speed=0
    time_new=[]
    speed_new=[]
    time_new_new=[]
    speed_new_new=[]
    if len(time) > 2:
        for i in range(1, len(speed)):
            if float(speed[i]) > 0 and float(speed[i]) < 1.8: #modify the threshold value accordingly
                time_new.append(float(time[i]))
                speed_new.append(float(speed[i]))
                distance += 1.0996
        if len(time_new) > 2:
            time_new_new.append(time_new[0])
            speed_new_new.append(speed_new[0])
            for ii in range(0, len(time_new)-1):
                if float(time_new[ii+1]) <= float(time_new[ii]) + 7: #the gap value can be changed accordingly
                    time_new_new.append(time_new[ii+1])
                    speed_new_new.append(speed_new[ii+1])
            average_speed = sum(speed_new_new)/len(speed_new_new)
        else:
            print('Cannot calculate distance and average speed')
    else:
        print('Cannot calculate distance and average speed')
    return (time_new_new, speed_new_new, distance, average_speed)  
    


#***************************************************************************************************************************
# This function returns duration of the shortest and longest bouts in seconds, entire flight duration in seconds and
# percentage of time spent in flight over time spent at rest. The function also returns the number of flying bouts of a
# specified duration and their respective duration expressed as percentage of the entire time spent in flight. In the example
# below the bouts duration were set at 60-300s, 300-900s, 900-3600s, 3600-14400s and >14400s. The recording time was set at
# 8h (28800s). 
#***************************************************************************************************************************

def flying_bouts(time, speed, ch):
    t_odd = []
    t_even = []
    tot_t = []
    last_time = 0
    diff = 0
    flight_time = 0
    longest_bout = 0
    shortest_bout = 0
    to_remove=[]
    bout_time = []
    fly_time=0 
    flight_60_300=[]
    sum_60_300=0
    flight_300_900=[]
    sum_300_900=0
    flight_900_3600=[]
    sum_900_3600=0
    flight_3600_14400=[]
    sum_3600_14400=0
    flight_14400=[]
    sum_14400=0
    events_300=0
    events_900=0
    events_3600=0
    events_14400=0
    events_more_14400=0
    if len(time) > 2:
        if float(time[1]) < float(time[0]) + 20:
            bout_time.append(time[0])

        #***************************************************************************************************************************
        #creates a list of time values where time gaps are no longer than 20s between consecutive time elements of the list
        #***************************************************************************************************************************
   
        for i in range(1, len(time)-1):
            if float(time[i+1]) >= float(time[i]) + 20:
                bout_time.append(time[i])
                bout_time.append(time[i+1])
        if bout_time[-1] != time[-1]:
            bout_time.append(time[-1])

        #***************************************************************************************************************************
        #clean the flying bout time event list from redundant values
        #***************************************************************************************************************************
    
        for iii in range(1, len(bout_time)):
            if float(bout_time[iii]) == float(bout_time[iii-1]):
                to_remove.append(bout_time[iii])
                                 
        for iiii in range(0, len(to_remove)):
            while to_remove[iiii] in bout_time:
                bout_time.remove(to_remove[iiii])
        
        #***************************************************************************************************************************
        #calculates the flight descriptive statistics
        #***************************************************************************************************************************
        
        if len(bout_time)%2 != 0:
            last_time = float(bout_time[-1])
            del bout_time[-1]
        t_odd = bout_time[0::2]
        t_even = bout_time[1::2]
        for ii in range(0, len(t_odd)):
            diff = float(t_even[ii]) - float(t_odd[ii])
            tot_t.append(diff)
        if float(last_time) != 0:
            diff = float(last_time) - float(t_even[-1])
            tot_t.append(diff)
        flight_time = sum(float(i) for i in tot_t)
        for index in range(0, len(tot_t)):
            if float(tot_t[index])>60 and float(tot_t[index])<=300:
                flight_60_300.append(float(tot_t[index])/flight_time)
            elif float(tot_t[index])>300 and float(tot_t[index])<=900:
                flight_300_900.append(float(tot_t[index])/flight_time)
            elif float(tot_t[index])>900 and float(tot_t[index])<=3600:
                flight_900_3600.append(float(tot_t[index])/flight_time)
            elif float(tot_t[index])>3600 and float(tot_t[index])<=14400:
                flight_3600_14400.append(float(tot_t[index])/flight_time)
            elif float(tot_t[index])>14400:
                flight_14400.append(float(tot_t[index])/flight_time)
        if len(flight_60_300) > 0:
            sum_60_300=sum(float(a) for a in flight_60_300)
            shortest_bout = float(min(flight_60_300))*flight_time
        if len(flight_300_900) > 0:
            sum_300_900=sum(float(b) for b in flight_300_900)
        if len(flight_900_3600) > 0:
            sum_900_3600=sum(float(c) for c in flight_900_3600)
        if len(flight_3600_14400) > 0:
            sum_3600_14400=sum(float(d) for d in flight_3600_14400)
        if len(flight_14400) > 0:
            sum_14400=sum(float(e) for e in flight_14400)           
        longest_bout = max(tot_t)
        fly_time=flight_time/28800          #total recording time defined by the user 
        events_300=len(flight_60_300)
        events_900=len(flight_300_900)
        events_3600=len(flight_900_3600)
        events_14400=len(flight_3600_14400)
        events_more_14400=len(flight_14400)
    else:
        print('Channel', ch, 'has only one peak - cannot perform calculation')

    return (flight_time, shortest_bout, longest_bout, fly_time, sum_60_300, sum_300_900, sum_900_3600, sum_3600_14400, sum_14400, events_300, events_900, events_3600, events_14400, events_more_14400)       


#***************************************************************************************************************************
# This function is used to to clean up the final time and speed variation file for each channel in order to produce
# clearer graphs.   
#***************************************************************************************************************************

def graph(time, speed):
    time_new=[]
    speed_new=[]
    x=0
    y=0
    for i in range(0, len(time)-1):
        if float(time[i+1]) > float(time[i]) + 20:
            time_new.append(time[i])
            speed_new.append(speed[i])
            x=float(time[i]) + 1
            time_new.append(x)
            speed_new.append(0)
            y=float(time[i+1]) -1
            time_new.append(y)
            speed_new.append(0)
        else:
            time_new.append(time[i])
            speed_new.append(speed[i])
    time_new.append(0)
    speed_new.append(0)
    return time_new, speed_new

#************************************************************************************************************
# The flight data file can be called by either defining the complete filepath (for example c:\desktop\recordings
# \filename.txt) or defining a default path in the section "write the path here" that will be automatically
# recalled each time the function is run. In the latter case the user will only need to type the name of
# the .txt or .dat file to process when requested.
#************************************************************************************************************

def cls(): print ("\n" * 100)

cls()
filename = input('File path or file name -> ')
input_file = open('C:/Users/wjrfo/Documents/Flight Mill/' + filename, "r")
print('Input file name ' + filename)
data_list = list(input_file)
time_column = []
list_dict=dict()
peaks1 = []
peaks2 = []
peaks3 = []
peaks4 = []
peaks5 = []
#peaks6 = []
#peaks7 = []
#peaks8 = []

print('Number of lines ' + str(len(data_list)))

for i in range(0, len(data_list)):
    raw = data_list[i]
#    a,b,c,d,e,f = raw.split(",") # if >5 channels then a,b,c,d,e,f,g,h,j
    a,b,c = raw.split(",") # if >5 channels then a,b,c,d,e,f,g,h,j
    time_column.append(a)
    peaks1.append(b)
    peaks2.append(c)
#    peaks3.append(d)
#    peaks4.append(e)
#    peaks5.append(f)
    #peaks6.append(g)
    #peaks7.append(h)
    #peaks8.append(j)

list_dict[1]=peaks1
list_dict[2]=peaks2
#list_dict[3]=peaks3
#list_dict[4]=peaks4
#list_dict[5]=peaks5
#list_dict[6]=peaks6
#list_dict[7]=peaks7
#list_dict[8]=peaks8

input_file.close()

for i in range(1, len(list_dict)+1):
    print('CHANNEL ' + str(i) + ' -------------------------------------------')
    time_channel = time_list(time_column, list_dict[i])
    speed_channel = speed_list(time_channel, i)
    time_n, speed_n, dist, av_speed = distance(time_channel, speed_channel)
    fly_time, short_bout, long_bout, flight, fly_to_300, fly_to_900, fly_to_3600, fly_to_14400, fly_more_14400, event_300, event_900, event_3600, event_14400, event_more_14400 = flying_bouts(time_n, speed_n, i)
    print('Average speed channel ' + str(i) + ' -> ' + '%.2f' % av_speed)
    print('Total flight time channel ' + str(i) + ' -> ' + '%.2f' % fly_time)
    print('Distance channel ' + str(i) + ' -> ' + '%.2f' % dist)
    print('Shortest flying bout channel ' + str(i) + ' -> ' + '%.2f' % short_bout)
    print('Longest flying bout channel ' + str(i) + ' -> ' + '%.2f' %long_bout)
    print('This individual spent ' + '%.3f' %flight + ' of its time flying with this composition: ')
    print('  60s-300s = ' + '%.3f' %fly_to_300 + ' with ',event_300, 'events')
    print('  300s-900s = ' + '%.3f' %fly_to_900 + ' with ',event_900, 'events')
    print('  900s-3600s = ' + '%.3f' %fly_to_3600 + ' with ',event_3600, 'events')
    print('  3600s-14400s = ' + '%.3f' %fly_to_14400 + ' with ',event_14400, 'events')
    print('  14400s = ' + '%.3f' %fly_more_14400 + ' with ',event_more_14400, 'events')
    print('\n')
    time_graph, speed_graph = graph(time_n, speed_n)
    OutputFile=open('C:/Users/wjrfo/Documents/Flight Mill\Channel'+str(i)+'.DAT', "w")
    for index in range(0, len(time_graph)):
        OutputFile.write('%.1f' % time_graph[index] + ',' + '%.2f' %speed_graph[index] + '\n')
    OutputFile.close()
